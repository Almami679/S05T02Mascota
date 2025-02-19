package ItAcademyJavaSpringBoot.AircraftFleet.Services.BattleService;


import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;

public interface BattleServiceInterface {
    Long getNextId();
    Battle addBattle(Long userId1, Long userId2, Long planeId1, Long planeId2);
    Battle getBattle(Long id);
    Battle executeBattle(Battle battle);
    BattlePlayerDTO determinateWinner(BattlePlayerDTO player1, BattlePlayerDTO player2);
    double calculateWinProbability(PlaneDTO plane1, PlaneDTO plane2);
    void defineScore(BattlePlayerDTO player1, BattlePlayerDTO player2, BattlePlayerDTO winner);

}
