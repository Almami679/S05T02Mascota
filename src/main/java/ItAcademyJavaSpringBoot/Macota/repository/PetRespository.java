package ItAcademyJavaSpringBoot.Macota.repository;

import ItAcademyJavaSpringBoot.Macota.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRespository extends JpaRepository<Pet, Integer> {
}
