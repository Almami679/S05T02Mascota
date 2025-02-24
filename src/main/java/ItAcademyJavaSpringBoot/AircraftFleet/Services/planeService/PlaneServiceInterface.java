package ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;

import java.util.List;

public interface PlaneServiceInterface {
    Plane getPlaneById(Long id);
    Plane getRandomPlaneFromOpponent(User playerOpponent);
    void applyBattlePlaneStatus(Long planeId, boolean wonBattle);
    Plane createPlanePurchasedByUser(User user, PlaneModel model);
    Plane equipAccessoryToPlane(Long planeId, PlaneAccessory accessory);
    Plane updatePlaneStats(Long planeId, PlaneAction action);
    PlaneAccessory getAccessoryForId(int accessoryId);
    void sellPlane(Plane plane);
}
