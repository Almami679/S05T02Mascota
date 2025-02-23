package ItAcademyJavaSpringBoot.AircraftFleet.model.sql;

import ItAcademyJavaSpringBoot.AircraftFleet.model.PlaneBuilder;
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

    @Getter
    private int health;
    @Getter
    private int baseHealth;
    @Getter
    private int attack;
    @Getter
    private int fuel;

    @OneToOne
    @JoinColumn(name = "equipped_accessory", unique = true)
    private PlaneAccessory equippedAccessory;

    @ManyToOne
    @JoinColumn(name = "hangar_id", nullable = false)
    private Hangar hangar;

    public Plane (String name, String model, int health, int attack, Hangar hangar){
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
        this.health = this.baseHealth;
    }

    public void refuel(){
        this.fuel = 100;
    }

    public void equipAccessory(PlaneAccessory newAccessory) {
        if (this.equippedAccessory != null) {
            this.equippedAccessory.setPlane(null);
        }
        this.equippedAccessory = newAccessory;
        newAccessory.setPlane(this);
    }
}
