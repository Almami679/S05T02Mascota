package ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService;

import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NotPlayerAvailableException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaneService planeService;


    public User getRandomPlayer() {
        List<User> players = userRepository.findAll();
        if (players.isEmpty()) {
            throw new NotPlayerAvailableException("No players available");
        }
        return players.get(new Random().nextInt(players.size()));
    }


    public Plane getRandomPlaneFromOpponent(User playerOpponent) {
        List<Plane> planes = playerOpponent.getHangar().getPlanes_ids()
                .stream()
                .map(planeService::getPlaneById)
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
}
