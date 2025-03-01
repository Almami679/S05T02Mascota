package ItAcademyJavaSpringBoot.AircraftFleet.model.sql;

import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.HealthPlaneIsFullException;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.PlaneHasFullFuelException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.PlaneBuilder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private int health;

    private int baseHealth;

    private int attack;

    private int fuel;



    @OneToOne
    @JoinColumn(name = "equipped_accessory", unique = true)
    private PlaneAccessory equippedAccessory;

    @ManyToOne
    @JoinColumn(name = "hangar_id", nullable = false)
    @JsonBackReference
    private Hangar hangar;

    public Plane (String name,String model, int health, int attack, Hangar hangar){
        this.name = name;
        this.model = model;
        this.health = health;
        this.baseHealth = health;
        this.attack = attack;
        this.hangar = hangar;
        this.fuel = 100;
        this.equippedAccessory = null;
    }

    public static PlaneBuilder builder() {
        return new PlaneBuilder();
    }

    public void repair(){
        if(health == baseHealth){
            throw new HealthPlaneIsFullException("This plane health is 100%");
        }
        this.health = this.baseHealth;
    }

    public void refuel(){
        if(fuel == 100){
            throw new PlaneHasFullFuelException("This plane has full Tank");
        }
        this.fuel = 100;
    }

    public void equipAccessory(PlaneAccessory newAccessory) {
        this.equippedAccessory = newAccessory;
    }
}
