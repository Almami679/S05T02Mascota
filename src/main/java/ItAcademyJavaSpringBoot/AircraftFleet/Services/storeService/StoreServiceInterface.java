package ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;

import java.util.List;
import java.util.Map;

public interface StoreServiceInterface {
    Hangar buyPlane(Long userId, String model);
    Plane buyAndEquipAccessory(Long userId, Long planeId, PlaneAccessoryModel accessoryModel);
    List<Map<String, Object>> getAvailablePlanes();
    List<Map<String, Object>> getAvailableAccessories();
}
