package ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;

public interface BattlesDTOConstructorsInterface {
    PlaneDTO planeToDto(Plane plane);
    BattlePlayerDTO userToDto(Long userId, Long planeId);
}
