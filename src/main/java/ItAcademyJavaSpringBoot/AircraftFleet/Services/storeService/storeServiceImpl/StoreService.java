package ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService.storeServiceImpl;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl.BattleService;
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
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneAccessoriesRepository;
import org.hibernate.annotations.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreService implements StoreServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HangarService hangarService;

    @Autowired
    private PlaneService planeService;

    @Autowired
    private PlaneAccessoriesRepository accessoryRepository;

    public Hangar buyPlane(Long userId, PlaneModel modelName) {
        User user = userService.findUserById(userId);

        userService.updateWallet(userId, modelName.getPrice(), StoreAction.PAY);

        log.info("Plane {} bought by {}", modelName.getName(), user.getUserName());

        return hangarService.addPlaneInHangar(
                userId,
                planeService.createPlanePurchasedByUser(user, modelName)
        );
    }

    public Plane buyAndEquipAccessory(Long userId, Long planeId, PlaneAccessoryModel accessory) {
        PlaneAccessory accessoryEntity = planeService.getAccessoryForId(accessory.getId());

        userService.updateWallet(userId, accessory.getPrice(), StoreAction.PAY);

        log.info("Accessory {} bought by user with id {}", accessory.getName(),userId );

        return planeService.equipAccessoryToPlane(planeId, accessoryEntity);

    }


    @Cacheable("List of planes in store")
    public List<Map<String, Object>> getAvailablePlanes() {
        log.info("Download Model planes for store");
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

    @Cacheable("List of accessories")
    public List<Map<String, Object>> getAvailableAccessories() {
        log.info("Download Accessory models for store");
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

