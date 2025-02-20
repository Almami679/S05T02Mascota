package ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService;

import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NotPlayerAvailableException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.StoreAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaneService planeService;

    @Autowired
    private HangarService hangarService;

    public User addNewUser(AuthRequestDTO newUser) {
        User userCreated = new User(newUser.getUserName(), newUser.getPassword(), newUser.getRole());
        userRepository.save(userCreated);
        userCreated.setHangar(hangarService.createNewHangarUser(userCreated));
        return userRepository.save(userCreated);
    }


    public User getRandomPlayer() {
        List<User> players = userRepository.findAll();
        if (players.isEmpty()) {
            throw new NotPlayerAvailableException("No players available");
        }
        return players.get(new Random().nextInt(players.size()));
    }


    public Plane getRandomPlaneFromOpponent(User playerOpponent) {
        List<Plane> planes = playerOpponent.getHangar().getPlanes()
                .stream()
                .map(plane -> planeService.getPlaneById(plane.getId()))
                .toList();
        if (planes.isEmpty()) {
            throw new IllegalStateException("Player has no planes");
        }
        return planes.get(new Random().nextInt(planes.size()));
    }

    public User editUserScore(Long id, double score) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotPlayerAvailableException("Player not found"));
        user.addPoints(score);
        return userRepository.save(user);
    }

    public User editUserWallet(Long id, double value, StoreAction action) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotPlayerAvailableException("Player not found"));

        if(action.equals(StoreAction.PAY)){
            user.setWallet(user.getWallet() - value);
        } else {
            user.setWallet(user.getWallet() + value);
        }
        return userRepository.save(user);
    }

    public boolean userIsPresent(String username) {
        return findUserByName(username) != null;
    }

    public User findUserByName(String username) {
        return userRepository.findByUserName(username);
    }
}
