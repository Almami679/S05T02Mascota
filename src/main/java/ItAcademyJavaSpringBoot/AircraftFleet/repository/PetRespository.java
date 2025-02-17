package ItAcademyJavaSpringBoot.AircraftFleet.repository;

import ItAcademyJavaSpringBoot.AircraftFleet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRespository extends JpaRepository<Pet, Integer> {
}
