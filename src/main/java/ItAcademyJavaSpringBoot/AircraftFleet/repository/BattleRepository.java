package ItAcademyJavaSpringBoot.AircraftFleet.repository;


import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattleRepository extends MongoRepository<Battle, String> {

    List<Battle> findByUser1_UsernameOrUser2_Username(String username1, String username2);
}
