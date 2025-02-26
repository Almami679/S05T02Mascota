package ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService;

import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;

import java.util.List;

public interface HangarServiceInterface {
    Hangar createNewHangarUser(User owner);
    Hangar getAllPlanesForUser(String username);
    Hangar addPlaneInHangar(Long userId, Plane plane);
    Hangar updateHangarState(Long userId);
}
