package ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService;


import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattleResultDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;

import java.util.List;

public interface BattleServiceInterface {
    BattlePlayerDTO findOpponent(Long userId);
    List<Battle> getAllBattles();
    Battle startBattle(Long userId, Long userPlaneId, BattlePlayerDTO opponent);
    Battle executeBattle(BattlePlayerDTO player1, BattlePlayerDTO player2);
    BattlePlayerDTO determineWinner(BattlePlayerDTO player1, BattlePlayerDTO player2);
    double calculateWinProbability(PlaneDTO plane1, PlaneDTO plane2);
    void applyBattleResults(BattlePlayerDTO player1, BattlePlayerDTO player2, BattlePlayerDTO winner);
    List<BattleResultDTO> getBattlesByUser(String username);
    int getNextId();

}
