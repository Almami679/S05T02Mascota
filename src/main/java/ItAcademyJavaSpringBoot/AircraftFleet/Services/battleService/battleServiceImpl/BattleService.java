package ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl;


import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.DTO.PlaneDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.BattleServiceInterface;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
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
public class BattleService implements BattleServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(BattleService.class);

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    BattlesDTOConstructors constructors;

    @Autowired
    private PlaneService planeService;

    public List<Battle> getAllBattles() {
        return battleRepository.findAll();
    }

    public BattlePlayerDTO findOpponent(Long userId) {
        User opponent = userService.getRandomOpponent(userId);
        Plane opponentPlane = planeService.getRandomPlaneFromOpponent(opponent);

        return constructors.userToDto(opponent.getId(), opponentPlane.getId());
    }

    public Battle startBattle(Long userId, Long userPlaneId, BattlePlayerDTO opponent) {

        BattlePlayerDTO player1DTO = constructors.userToDto(userId, userPlaneId);

        Battle battle = executeBattle(player1DTO, opponent);

        return battleRepository.save(battle);
    }

    public Battle executeBattle(BattlePlayerDTO player1, BattlePlayerDTO player2) {
        logger.info("Executing battle between {} and {}", player1.getUsername(), player2.getUsername());

        BattlePlayerDTO winner = determineWinner(player1, player2);
        Battle battle = new Battle(player1, player2);
        battle.setWinner(winner);

        applyBattleResults(player1, player2, winner);

        return battle;
    }

    public BattlePlayerDTO determineWinner(BattlePlayerDTO player1, BattlePlayerDTO player2) {
        double probWinPlayer1 = calculateWinProbability(player1.getPlane(), player2.getPlane());
        return (Math.random() < probWinPlayer1) ? player1 : player2;
    }

    public double calculateWinProbability(PlaneDTO plane1, PlaneDTO plane2) {
        double score1 = plane1.getAttack() * 0.6 + plane1.getHealth() * 0.4;
        double score2 = plane2.getAttack() * 0.6 + plane2.getHealth() * 0.4;
        return score1 / (score1 + score2);
    }

    public void applyBattleResults(BattlePlayerDTO player1, BattlePlayerDTO player2, BattlePlayerDTO winner) {
        if (player1.equals(winner)) {
            planeService.applyBattlePlaneStatus(player1.getPlane().getPlaneId(), true);
            planeService.applyBattlePlaneStatus(player2.getPlane().getPlaneId(), false);
            userService.addScore(player1.getUserId(), 130);
            userService.addScore(player2.getUserId(), 60);
        } else {
            planeService.applyBattlePlaneStatus(player2.getPlane().getPlaneId(), true);
            planeService.applyBattlePlaneStatus(player1.getPlane().getPlaneId(), false);
            userService.addScore(player2.getUserId(), 130);
            userService.addScore(player1.getUserId(), 60);
        }
    }

    public List<Battle> getBattlesByUser(String username) {
        return battleRepository.findByUser1_UsernameOrUser2_Username(username, username);
    }
}

