package ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.BattlesDTOConstructorsInterface;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattlesDTOConstructors implements BattlesDTOConstructorsInterface {
    @Autowired
    private UserService userService;
    @Autowired
    private PlaneService planeService;

    public PlaneDTO planeToDto(Plane plane) {
        return new PlaneDTO(plane.getId(),
                plane.getHealth(),
                plane.getAttack(),
                plane.getFuel(),
                plane.getEquippedAccessory());
    }

    public BattlePlayerDTO userToDto(Long userId, Long planeId) {
        User player = userService.findUserById(userId);
        PlaneDTO plane = planeToDto(planeService.getPlaneById(planeId));

        return new BattlePlayerDTO(userId,
                player.getUserName(),
                plane,
                player.getScore());
    }
}
