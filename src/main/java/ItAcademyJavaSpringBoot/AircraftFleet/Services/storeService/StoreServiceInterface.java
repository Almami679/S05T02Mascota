package ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;

import java.util.List;
import java.util.Map;

public interface StoreServiceInterface {
    Hangar buyPlane(Long userId, PlaneModel model);
    Plane buyAndEquipAccessory(Long userId, Long planeId, String accessoryName);
    List<Map<String, Object>> getAvailablePlanes();
    List<Map<String, Object>> getAvailableAccessories();
}
