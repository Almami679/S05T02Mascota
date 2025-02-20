package ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService;

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

    public PlaneBuilder setModel(String model) {
        this.model = model;
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

    public PlaneBuilder setFuel(int fuel) {
        this.fuel = fuel;
        return this;
    }

    public PlaneBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public PlaneBuilder setHangar(Hangar hangar) {
        this.hangar = hangar;
        return this;
    }

    public Plane build() {
        return new Plane(name, model, health, attack, hangar);
    }
}


