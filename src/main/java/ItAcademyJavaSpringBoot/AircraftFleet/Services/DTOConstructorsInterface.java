package ItAcademyJavaSpringBoot.AircraftFleet.Services;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattleResultDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.RankingDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;

import java.util.List;

public interface DTOConstructorsInterface {
    PlaneDTO planeToDto(Plane plane);
    BattlePlayerDTO userToDto(Long userId, Long planeId);
    List<BattlePlayerDTO> getDTOForAllPlanes(List<Hangar> allHangars);
    BattleResultDTO getResultDTO(String username, Battle battle);
    RankingDTO mapToDTO(User user);
}
