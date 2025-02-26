package ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums;

import lombok.Getter;

@Getter
public enum PlaneAccessoryModel {

    MG_42(1,AccessoryType.GUN, "MG 42", 1, 50, 500.0),
    CANNON_20MM(3,AccessoryType.GUN, "Cañón 20mm Hispano", 2, 80, 750.0),
    COHETES_V2(3,AccessoryType.GUN, "Cohete V2 Alemán", 3, 120, 1200.0),

    LIGHT_ARMOR(4, AccessoryType.ARMOR, "Blindaje Ligero", 1, 20, 400.0),
    MEDIUM_ARMOR(5,AccessoryType.ARMOR, "Blindaje Medio", 2, 35, 600.0),
    HEAVY_ARMOR(6,AccessoryType.ARMOR, "Blindaje Pesado", 3, 50, 900.0);

    private final int id;
    private final AccessoryType type;
    private final String name;
    private final int level;
    private final int power;
    private final double price;

    PlaneAccessoryModel(int id, AccessoryType type, String name, int level, int power, double price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.level = level;
        this.power = power;
        this.price = price;
    }

}



