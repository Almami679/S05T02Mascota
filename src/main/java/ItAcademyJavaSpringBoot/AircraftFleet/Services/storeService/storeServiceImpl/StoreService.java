package ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService.storeServiceImpl;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService.StoreServiceInterface;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.hangarServiceImpl.HangarService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.AccessoryNotFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.StoreAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreService implements StoreServiceInterface {

    @Autowired
    private UserService userService;

    @Autowired
    private HangarService hangarService;

    @Autowired
    private PlaneService planeService;

    public Hangar buyPlane(Long userId, PlaneModel model) {
        User user = userService.findUserById(userId);
        userService.updateWallet(userId, model.getPrice(), StoreAction.PAY);

        return hangarService.addPlaneInHangar(
                userId,
                planeService
                        .createPlanePurchasedByUser(user, model));
    }

    public Plane buyAndEquipAccessory(Long userId, Long planeId, String accessoryName) {
        PlaneAccessoryModel accessoryModel = Arrays.stream(PlaneAccessoryModel.values())
                .filter(a -> a.getName().equalsIgnoreCase(accessoryName))
                .findFirst()
                .orElseThrow(() -> new AccessoryNotFoundException("El accesorio no existe"));

        userService.updateWallet(userId, accessoryModel.getPrice(), StoreAction.PAY);
        return planeService.equipAccessoryToPlane(planeId, accessoryModel.getId());
    }

    public List<Map<String, Object>> getAvailablePlanes() {
        return Arrays.stream(PlaneModel.values())
                .map(model -> Map.<String, Object>of(
                        "name", model.getName(),
                        "description", model.getDescription(),
                        "health", model.getHealth(),
                        "attack", model.getAttack(),
                        "price", model.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAvailableAccessories() {
        return Arrays.stream(PlaneAccessoryModel.values())
                .map(accessory -> Map.<String, Object>of(
                        "name", accessory.getName(),
                        "type", accessory.getType(),
                        "level", accessory.getLevel(),
                        "power", accessory.getPower(),
                        "price", accessory.getPrice()
                ))
                .collect(Collectors.toList());
    }
}

