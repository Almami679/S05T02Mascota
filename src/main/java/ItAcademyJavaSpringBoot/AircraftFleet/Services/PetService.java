package ItAcademyJavaSpringBoot.AircraftFleet.Services;


import ItAcademyJavaSpringBoot.AircraftFleet.repository.PetRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRespository repository;


}
