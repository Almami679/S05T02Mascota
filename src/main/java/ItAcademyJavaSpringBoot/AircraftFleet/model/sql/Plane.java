package ItAcademyJavaSpringBoot.AircraftFleet.model.sql;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService.PlaneBuilder;
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

    public Plane (String name, String model, int health, int attack, Hangar hangar){
        this.name = name;
        this.model = model;
        this.health = health;
        this.attack = attack;
        this.hangar = hangar;
        this.fuel = 100;
        this.accessories = new ArrayList<>();
    }

    public static PlaneBuilder builder() {
        return new PlaneBuilder();
    }

}
