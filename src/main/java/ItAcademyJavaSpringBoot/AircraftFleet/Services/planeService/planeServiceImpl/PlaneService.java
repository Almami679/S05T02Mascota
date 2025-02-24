package ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.hangarServiceImpl.HangarService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.PlaneServiceInterface;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.AccessoryNotFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NoAccessoryFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NoPlaneFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.PlayerHasNoPlanesException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneAccessoriesRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class PlaneService implements PlaneServiceInterface {

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

    public Plane getRandomPlaneFromOpponent(User playerOpponent) {
        List<Plane> planes = hangarService.getAllPlanesForUser(playerOpponent.getUserName());

        if (planes.isEmpty()) {
            throw new PlayerHasNoPlanesException("Player has no planes");
        }

        return planes.get(new Random().nextInt(planes.size()));
    }

    public void applyBattlePlaneStatus(Long planeId, boolean wonBattle) {
        Plane plane = planeRepository.findById(planeId)
                .orElseThrow(() -> new NoPlaneFoundException("Plane with id [" + planeId + "] not found"));

        if (wonBattle) {
            plane.setFuel(0);
            plane.setHealth(30 + (int) (Math.random() * 61));
            planeRepository.save(plane);
        } else {
            planeRepository.delete(plane);
        }
    }

    public Plane createPlanePurchasedByUser(User user, PlaneModel model) {
        Plane plane = Plane.builder()
                .setName(model.getName())
                .setModel(model.name())
                .setHealth(model.getHealth())
                .setAttack(model.getAttack())
                .setHangar(user.getHangar())
                .build();

        return planeRepository.save(plane);
    }

    public Plane equipAccessoryToPlane(Long planeId, PlaneAccessory accessory) {
        Plane plane = getPlaneById(planeId);

        plane.equipAccessory(accessory);

        return planeRepository.save(plane);
    }

    public Plane updatePlaneStats(Plane plane, PlaneAction action) {
        switch (action) {
            case REFUEL -> plane.refuel();
            case REPAIR -> plane.repair();
            case CHANGESKIN -> {} //QUE POLLAS TENGO QUE HACER PARA LA SKINS
        }
        return planeRepository.save(plane);
    }

    public PlaneAccessory getAccessoryForId(int accessoryId) {
        return accessoriesRepository.findById(accessoryId)
                .orElseThrow(() -> new AccessoryNotFoundException("El accesorio no existe"));
    }
}


