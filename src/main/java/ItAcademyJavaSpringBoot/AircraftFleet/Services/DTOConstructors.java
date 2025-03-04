package ItAcademyJavaSpringBoot.AircraftFleet.Services;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattleResultDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.RankingDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl.BattleService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DTOConstructors implements DTOConstructorsInterface {
    private static final Logger log = LoggerFactory.getLogger(DTOConstructors.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PlaneService planeService;

    public PlaneDTO planeToDto(Plane plane) {
        log.info("PlaneDTO created");
        return new PlaneDTO(plane.getId(),
                plane.getHealth(),
                plane.getAttack(),
                plane.getFuel(),
                plane.getEquippedAccessory(),
                plane.getName());
    }

    public BattlePlayerDTO userToDto(Long userId, Long planeId) {
        User player = userService.findUserById(userId);
        PlaneDTO plane = planeToDto(planeService.getPlaneById(planeId));
        log.info("BattlePlayerDTO created with [user: {} | planeId: {}] .",player.getUserName(), plane.getPlaneId());
        return new BattlePlayerDTO(userId,
                player.getUserName(),
                plane,
                player.getScore());
    }

    public BattleResultDTO getResultDTO(String username, Battle battle) {
        boolean isWinner = battle.getWinner().getUsername().equals(username);
        PlaneDTO opponent = battle.getUser1().getUsername().equals(username)
                ? battle.getUser2().getPlane()
                : battle.getUser1().getPlane();
        log.info("BattleDTO result created");
        return new BattleResultDTO(isWinner, battle.getWinner().getScore(), opponent, battle.getBattleDate());
    }

    public List<BattlePlayerDTO> getDTOForAllPlanes(List<Hangar> allHangars) {
        return allHangars.stream()
                .flatMap(hangar ->
                        hangar.getPlanes().stream()
                                .map(plane -> userToDto(
                                        hangar.getOwner().getId(),
                                        plane.getId()
                                ))
                )
                .collect(Collectors.toList());
    }

    public RankingDTO mapToDTO(User user) {

        RankingDTO dto = new RankingDTO();
        dto.setUsername(user.getUserName());
        dto.setScore(user.getScore());
        dto.setLevel();
        return dto;
    }
}
