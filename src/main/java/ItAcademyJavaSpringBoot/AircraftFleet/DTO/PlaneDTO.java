package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.AccessoryType;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaneDTO {
    private Long planeId;
    private int health;
    private int attack;
    private int fuel;

    public PlaneDTO (Long planeId, int health, int attack, int fuel, PlaneAccessory accessory) {
        this.planeId = planeId;
        this.health = health;
        this.attack = attack;
        this.fuel = fuel;
        if(accessory != null) {
            getStatsWithBonus(accessory);
        }
    }

    public void getStatsWithBonus(PlaneAccessory accessory) {
        if(accessory.getType()== AccessoryType.GUN){
            this.attack += accessory.getPower();
        } else {
            this.health += accessory.getPower();
        }
    }

}
