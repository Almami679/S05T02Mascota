package ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.hangarServiceImpl;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.HangarServiceInterface;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.HangarRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HangarService implements HangarServiceInterface {

    @Autowired
    private HangarRepository hangarRepository;

    @Autowired
    private UserService userService;

    public Hangar createNewHangarUser(User owner) {
        Hangar newHangar = new Hangar(owner);
        return hangarRepository.save(newHangar);
    }

    public Hangar getAllPlanesForUser(String username) {
        Hangar hangar = userService.findUserByName(username).getHangar();
        hangar.updateHangarState();
        return hangarRepository.save(hangar);
    }

    public Hangar addPlaneInHangar(Long userId, Plane plane) {
        Hangar hangar = userService.findUserById(userId).getHangar();
        hangar.addPlaneInHangar(plane);
        return hangarRepository.save(hangar);
    }

    public Hangar updateHangarState(Long userId) {
        User user = userService.findUserById(userId);
        Hangar hangar = user.getHangar();
        hangar.updateHangarState();
        return hangarRepository.save(hangar);
    }

    public List<Hangar> getAllHangars(){
        return hangarRepository.findAll();
    }

}
