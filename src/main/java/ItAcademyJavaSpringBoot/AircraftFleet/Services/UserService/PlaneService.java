package ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NoPlaneFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneAccessoryDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.PlayerHasNoPlanesException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneAccessoriesRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PlaneService {

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private PlaneAccessoriesRepository accessoriesRepository;

    @Autowired
    private HangarService hangarService;

    public Plane getPlaneById(Long id) {
        return planeRepository.findById(id)
                .orElseThrow(() -> new NoPlaneFoundException("Plane with id [" + id + "] not found"));
    }


    public List<PlaneAccessoryDTO> getAccessoriesForPlane(Long planeId) {
        return accessoriesRepository.getAllAccessoriesForPlane(planeId)
                .stream()
                .map(accessory -> new PlaneAccessoryDTO(
                        accessory.getName(),
                        accessory.getType().name(),
                        accessory.getPower()))
                .toList();
    }


    public Plane getRandomPlaneFromOpponent(User playerOpponent) {
        List<Plane> planes = hangarService.getAllPlanesForUser(playerOpponent.getUserName());

        if (planes.isEmpty()) {
            throw new PlayerHasNoPlanesException("Player has no planes");
        }

        return planes.get(new Random().nextInt(planes.size()));
    }


    public PlaneDTO getPlaneDto(Long id) {
        Plane plane = getPlaneById(id);
        List<PlaneAccessoryDTO> accessories = getAccessoriesForPlane(id);

        return new PlaneDTO(
                plane.getId(),
                plane.getName(),
                plane.getModel(),
                plane.getHealth(),
                plane.getAttack(),
                plane.getFuel(),
                accessories
        );
    }

    public Plane updatePlaneAfterBattle(PlaneDTO updatedPlane) {
        planeRepository.findById(updatedPlane.getId())
                .orElseThrow(() -> new NoPlaneFoundException("Fruit not found with id " + updatedPlane.getId()));
        Plane dbPlane = planeRepository.getReferenceById(updatedPlane.getId());
        dbPlane.setHealth(updatedPlane.getBaseHealth());
        dbPlane.setFuel(updatedPlane.getFuel());
        return planeRepository.save(dbPlane);
    }

    public void deletePlane(Long id) {
        planeRepository.findById(id)
                .orElseThrow(() -> new NoPlaneFoundException("Plane not found with id " + id));
        planeRepository.deleteById(id);
    }

}
