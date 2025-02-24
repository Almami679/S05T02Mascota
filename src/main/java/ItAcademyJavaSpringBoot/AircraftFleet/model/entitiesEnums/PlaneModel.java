package ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums;

import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NoPlaneFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PlaneModel {
    SPITFIRE("Spitfire",
            "Famoso caza británico de la RAF",
            120, 75, 2500.0),
    MESSERSCHMITT_BF109("Messerschmitt Bf 109",
            "Caza alemán versátil y rápido",
            110, 80, 2400.0),
    P51_MUSTANG("P-51 Mustang",
            "Avión de escolta estadounidense con gran autonomía",
            130, 85, 2700.0),
    ZERO("Mitsubishi A6M Zero",
            "Ligero y ágil, utilizado por la Armada Imperial Japonesa",
            100, 90, 2300.0),
    FOCKE_WULF("Focke-Wulf Fw 190",
            "Caza alemán robusto y poderoso",
            140, 95, 2800.0),
    YAK_3("Yakovlev Yak-3",
            "Caza soviético ágil y veloz en combate",
            115, 78, 2600.0);

    private final String name;
    private final String description;
    private final int health;
    private final int attack;
    private final double price;

    PlaneModel(String name, String description, int health, int attack, double price) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.attack = attack;
        this.price = price;
    }

    public static Double getPriceByPlaneName(String selectedPlane) {
        return Arrays.stream(PlaneModel.values())
                .filter(plane -> plane.getName().equalsIgnoreCase(selectedPlane))
                .findFirst()
                .map(PlaneModel::getPrice)
                .orElseThrow(() -> new NoPlaneFoundException("Plane not found with name " + selectedPlane));
    }
}