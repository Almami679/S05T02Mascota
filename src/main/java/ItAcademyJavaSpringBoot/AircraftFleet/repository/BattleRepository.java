package ItAcademyJavaSpringBoot.AircraftFleet.repository;


import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BattleRepository extends MongoRepository<Battle, String> {

    Optional<Battle> findTopByOrderByIdDesc();
}
