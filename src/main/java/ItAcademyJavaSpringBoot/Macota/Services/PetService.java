package ItAcademyJavaSpringBoot.Macota.Services;


import ItAcademyJavaSpringBoot.Macota.repository.PetRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRespository repository;


}
