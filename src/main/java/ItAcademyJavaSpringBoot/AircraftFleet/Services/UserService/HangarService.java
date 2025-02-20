package ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService;

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
public class HangarService {

    @Autowired
    private HangarRepository hangarRepository;

    @Autowired
    private UserService userService;

    public Hangar createNewHangarUser(User owner) {
        Hangar newHangar = new Hangar(owner);
        return hangarRepository.save(newHangar);
    }

    public List<Plane> getAllPlanesForUser(String username) {
        Long hangarId = userService.findUserByName(username).getHangar().getId();
        return hangarRepository.getAllPlanesForHangarId(hangarId);

    }


}
