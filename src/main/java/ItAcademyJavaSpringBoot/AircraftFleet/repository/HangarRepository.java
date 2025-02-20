package ItAcademyJavaSpringBoot.AircraftFleet.repository;

import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HangarRepository extends JpaRepository<Hangar, Long> {

    @Query("SELECT h.planes FROM Hangar h WHERE h.id = :hangarId")
    List<Plane> getAllPlanesForHangarId(@Param("hangarId") Long hangarId);
}
