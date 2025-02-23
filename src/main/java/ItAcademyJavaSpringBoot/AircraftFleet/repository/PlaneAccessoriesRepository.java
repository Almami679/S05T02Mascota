package ItAcademyJavaSpringBoot.AircraftFleet.repository;

import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneAccessoriesRepository extends JpaRepository<PlaneAccessory, Integer> {
}
