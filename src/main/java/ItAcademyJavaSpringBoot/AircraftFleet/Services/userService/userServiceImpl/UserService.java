package ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.RankingDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.DTOConstructors;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl.BattleService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.hangarServiceImpl.HangarService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.UserServiceInterface;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.InsufficientCreditsException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NotPlayerAvailableException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.StoreAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private DTOConstructors constructor;

    @Autowired
    @Lazy //esto hace que no se quede en loop cargando el servicio
    private HangarService hangarService;

    public User addNewUser(AuthRequestDTO newUser) {
        User userCreated = new User(newUser.getUserName(), newUser.getPassword(), newUser.getRole());
        userRepository.save(userCreated);
        userCreated.setHangar(hangarService.createNewHangarUser(userCreated));
        log.info("New user added: {}", userCreated.getUserName());
        return userRepository.save(userCreated);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public User findUserByName(String username) {
        return userRepository.findByUserName(username);
    }

    public User updateWallet(Long userId, double amount, StoreAction action) {
        User user = findUserById(userId);
        if(user.getRole() != Role.ADMIN) {
            if (action == StoreAction.PAY && user.getWallet() < amount) {
                throw new InsufficientCreditsException("Saldo insuficiente para comprar.");
            }
            user.setWallet(action == StoreAction.PAY ? user.getWallet() - amount : user.getWallet() + amount);
        }
        log.info("Action {} with {} amount for player {}, in progres",action, amount, user.getUserName());
        return userRepository.save(user);
    }

    public void addScore(Long userId, double score) {
        User user = findUserById(userId);
        user.addPoints(score);
        log.info("{}'s Score modified success", user.getUserName());
        userRepository.save(user);
    }

    public User addCredits(Long userId, double coins) {
        log.info("{} coins added to userId{}'s wallet", coins, userId);
        return updateWallet(userId, coins, StoreAction.ADD);
    }

    public User getRandomOpponent(Long userId) {
        List<User> players = userRepository.findByRole(Role.USER).stream()
                .filter(user -> !user.getId().equals(userId))
                .toList();

        if (players.isEmpty()) {
            throw new NotPlayerAvailableException("No opponents available");
        }

        log.info("Random player found");
        return players.get(new Random().nextInt(players.size()));
    }

    public boolean userIsPresent(String username) {
        return userRepository.existsByUserName(username);
    }

    public List<RankingDTO> getRankingByScoreDesc() {
        return userRepository.findAllByOrderByScoreDesc().stream()
                .map(user -> constructor.mapToDTO(user))
                .collect(Collectors.toList());
    }
}

