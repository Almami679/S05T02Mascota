package ItAcademyJavaSpringBoot.AircraftFleet.model.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Table(name = "planes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String model;

    @Getter
    private int health;
    @Getter
    private int attack;
    @Getter
    private int fuel;

    @Getter
    private ArrayList<Long> accessories;

    @ManyToOne
    @JoinColumn(name = "hangar_id", nullable = false)
    private Hangar hangar;

}
