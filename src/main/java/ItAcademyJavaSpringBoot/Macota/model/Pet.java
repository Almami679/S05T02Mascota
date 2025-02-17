package ItAcademyJavaSpringBoot.Macota.model;

import ItAcademyJavaSpringBoot.Macota.model.entityEnums.PetStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private PetStatus status;


    public Pet() {}



}
