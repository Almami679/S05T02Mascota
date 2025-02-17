package ItAcademyJavaSpringBoot.AircraftFleet.repository;

import ItAcademyJavaSpringBoot.AircraftFleet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
}
