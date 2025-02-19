package ItAcademyJavaSpringBoot.AircraftFleet.repository;

import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneAccessoriesRepository extends JpaRepository<PlaneAccessory, Long> {

    List<PlaneAccessory> getAllAccessoriesForPlane(Long planeId);
}
