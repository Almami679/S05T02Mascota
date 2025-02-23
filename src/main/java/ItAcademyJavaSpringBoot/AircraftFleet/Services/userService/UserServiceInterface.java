package ItAcademyJavaSpringBoot.AircraftFleet.Services.userService;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.StoreAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;

public interface UserServiceInterface {
    User addNewUser(AuthRequestDTO newUser);
    User findUserById(Long id);
    User findUserByName(String username);
    User updateWallet(Long userId, double amount, StoreAction action);
    void addScore(Long userId, double score);
    User addCredits(Long userId, double coins);
    User getRandomOpponent(Long userId);
    boolean userIsPresent(String username);
}
