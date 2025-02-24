package ItAcademyJavaSpringBoot.AircraftFleet.model;

import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;

public class PlaneBuilder {
    private String name;
    private String model;
    private int health;
    private int attack;
    private Hangar hangar;

    public PlaneBuilder setName(String name) {
        this.name = name;
        return this;
    }


    public PlaneBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    public PlaneBuilder setAttack(int attack) {
        this.attack = attack;
        return this;
    }

    public PlaneBuilder setHangar(Hangar hangar) {
        this.hangar = hangar;
        return this;
    }

    public Plane build() {
        return new Plane(name, health, attack, hangar);
    }
}


