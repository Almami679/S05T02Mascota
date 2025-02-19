package ItAcademyJavaSpringBoot.AircraftFleet.repository;


import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleRepository extends MongoRepository<Battle, Long> {
}
