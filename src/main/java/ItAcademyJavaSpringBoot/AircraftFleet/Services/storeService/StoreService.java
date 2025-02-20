package ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.InsufficientCreditsException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.StoreAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.HangarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private HangarRepository hangarRepository;

    public Plane buyPlane(User user, PlaneModel model) {
        if (user.getWallet() < model.getPrice()) {
            throw new InsufficientCreditsException("Fondos insuficientes para comprar este avión");
        }

        userService.editUserWallet(user.getId(), model.getPrice(), StoreAction.PAY);

        Plane plane = Plane.builder()
                .setName(model.getName())
                .setModel(model.name())
                .setHealth(model.getHealth())
                .setAttack(model.getAttack())
                .setFuel(100)
                .setPrice(model.getPrice())
                .setHangar(user.getHangar())
                .build();

        return planeRepository.save(plane);
    }


    public List<Map<String, Object>> getAvailablePlanes() {
        return Arrays.stream(PlaneModel.values())
                .map(model -> Map.<String, Object>of(
                        "name", model.getName(),
                        "description", model.getDescription(),
                        "health", (int) model.getHealth(),
                        "attack", (int) model.getAttack(),
                        "price", (double) model.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public User addCredits(User user, double value){
        return userService.editUserWallet(user.getId(), value, StoreAction.ADD);
    }


}
