package ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl.BattleService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.hangarServiceImpl.HangarService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.PlaneServiceInterface;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.AccessoryNotFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NoPlaneFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.PlaneNotFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.PlayerHasNoPlanesException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.StoreAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneAccessoriesRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PlaneService implements PlaneServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(PlaneService.class);

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private PlaneAccessoriesRepository accessoriesRepository;

    @Autowired
    UserService userService;

    @Autowired
    private HangarService hangarService;

    public Plane getPlaneById(Long id) {
        return planeRepository.findById(id)
                .orElseThrow(() -> new NoPlaneFoundException("Plane with id [" + id + "] not found"));
    }

    public Plane getRandomPlaneFromOpponent(User playerOpponent) {
        List<Plane> planes = hangarService.getAllPlanesForUser(playerOpponent.getUserName()).getPlanes();

        if (planes.isEmpty()) {
            return null;
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
            log.info("Battle finished, plane stats: heath[{}], fuel[{}]",plane.getHealth(), plane.getFuel());
        } else {
            planeRepository.delete(plane);
        }
    }

    public Plane createPlanePurchasedByUser(User user, PlaneModel model) {
        Plane plane = Plane.builder()
                .setName(model.getName())
                .setModel(model.getDescription())
                .setHealth(model.getHealth())
                .setAttack(model.getAttack())
                .setHangar(user.getHangar())
                .build();

        log.info("Plane {} created for user {}", plane.getName(), user.getUserName());

        return planeRepository.save(plane);
    }

    public Plane equipAccessoryToPlane(Long planeId, PlaneAccessory accessory) {
        Plane plane = getPlaneById(planeId);

        plane.equipAccessory(accessory);

        log.info("Accessory {} added to plane with id {}", accessory.getName(), plane.getId());

        return planeRepository.save(plane);
    }

    public Plane updatePlaneStats(Long planeId, PlaneAction action, Long userId) {
        Plane plane = getPlaneById(planeId);
        if (action == PlaneAction.SELL) {
            sellPlane(plane.getId());
            return plane;
        } else {
            switch (action) {
                case REFUEL -> {
                    plane.refuel();
                    userService.updateWallet(userId, 500, StoreAction.PAY);
                    log.info("Plane refuel success");
                }
                case REPAIR -> {
                    plane.repair();
                    userService.updateWallet(userId, 800, StoreAction.PAY);
                    log.info("plane repair success");
                }
            }
            return planeRepository.save(plane);
        }
    }

    public PlaneAccessory getAccessoryForId(int accessoryId) {
        return accessoriesRepository.findById(accessoryId)
                .orElseThrow(() -> new AccessoryNotFoundException("El accesorio no existe"));
    }

    public void sellPlane(Long planeId) {
        Plane plane = planeRepository.findById(planeId).orElseThrow(() -> new PlaneNotFoundException("Avion no encontrado"));
        Long userId = plane.getHangar().getOwner().getId();
        double price = PlaneModel.getPriceByPlaneName(plane.getName()) / 2;
        userService.updateWallet(userId, price, StoreAction.ADD);
        log.info("Plane with id {} sell success", plane.getId());
        planeRepository.delete(plane);
    }
}


