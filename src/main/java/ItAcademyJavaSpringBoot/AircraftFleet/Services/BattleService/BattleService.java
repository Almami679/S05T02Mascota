package ItAcademyJavaSpringBoot.AircraftFleet.Services.BattleService;


import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.BattleNotFoundException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.BattleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleService {

    private static final Logger logger = LoggerFactory.getLogger(BattleService.class);

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaneService planeService;


    public List<Battle> getAllBattles() {
        logger.info("Fetching all battles");
        return battleRepository.findAll();
    }

    public Battle createNewBattle(BattlePlayerDTO player){
        BattlePlayerDTO player1 = player;
        BattlePlayerDTO player2 = searchOpponent();

        Battle battle = new Battle(player1, player2);

        battle = executeBattle(battle);

        return battleRepository.save(battle);
    }

    public Battle getBattle(Long id) {
        logger.info("Fetching battle with ID: {}", id);
        return battleRepository.findById(id)
                .orElseThrow(() -> new BattleNotFoundException("Battle not found with id " + id));
    }

    public Battle addBattle(Battle battle) {
        logger.info("Creating a new battle between {} and {}", battle.getUser1().getUsername(), battle.getUser2().getUsername());
        return battleRepository.save(executeBattle(battle));
    }

    public Battle executeBattle(Battle battle) {
        logger.info("Executing battle between {} and {}", battle.getUser1().getUsername(), battle.getUser2().getUsername());

        BattlePlayerDTO winner = determinateWinner(battle.getUser1(), battle.getUser2());
        battle.setWinner(winner);

        setUserByBattle(battle.getUser1(), battle.getUser2(), winner);

        return battle;
    }

    private BattlePlayerDTO determinateWinner(BattlePlayerDTO player1, BattlePlayerDTO player2) {
        double probWinPlayer1 = calculateWinProbability(player1.getPlane(), player2.getPlane());
        return (Math.random() < probWinPlayer1) ? player1 : player2;
    }

    private double calculateWinProbability(PlaneDTO plane1, PlaneDTO plane2) {
        double score1 = plane1.getTotalAttack() * 0.6 + plane1.getTotalAttack() * 0.4;
        double score2 = plane2.getTotalHealth() * 0.6 + plane2.getTotalHealth() * 0.4;
        return score1 / (score1 + score2);
    }

    private User defineScore(boolean playerWins, Long playerId) {
        double winnerPoints = 130;
        double loserPoints = 60;

        return playerWins ? userService.editUserScore(playerId, winnerPoints) : userService.editUserScore(playerId, loserPoints);
    }

    private void setUserByBattle(BattlePlayerDTO player1, BattlePlayerDTO player2, BattlePlayerDTO winner) {
        if (player1.equals(winner)) {
            PlaneDTO planePlayer1 = planeService.getPlaneDto(player1.getPlane().getId());
            planePlayer1.setFuel(0);
            planePlayer1.setBaseHealth(30 + (int) (Math.random() * 61));
            planeService.updatePlaneAfterBattle(planePlayer1);
            planeService.deletePlane(player2.getPlane().getId());
            defineScore(true, player1.getUserId());
        } else {
            PlaneDTO planePlayer2 = planeService.getPlaneDto(player2.getPlane().getId());
            planePlayer2.setFuel(0);
            planePlayer2.setBaseHealth(30 + (int) (Math.random() * 61));
            planeService.updatePlaneAfterBattle(planePlayer2);
            planeService.deletePlane(player1.getPlane().getId());
            defineScore(false, player1.getUserId());
        }
    }

    public BattlePlayerDTO searchOpponent() {
        User opponent = userService.getRandomPlayer();
        Plane opponentPlane = planeService.getRandomPlaneFromOpponent(opponent);

        return new BattlePlayerDTO(
                opponent.getId(),
                opponent.getUserName(),
                planeService.getPlaneDto(opponentPlane.getId()),
                opponent.getScore());
    }
}
