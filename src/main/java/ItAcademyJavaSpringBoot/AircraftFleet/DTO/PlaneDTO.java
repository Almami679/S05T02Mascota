package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.AccessoryType;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.PlaneAccessory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaneDTO {

    private Long planeId;
    private String name;
    private int health;
    private int attack;
    private int fuel;
    private PlaneAccessory planeAccessory;

    public PlaneDTO (Long planeId,
                     int health,
                     int attack,
                     int fuel,
                     PlaneAccessory accessory, String name) {
        this.planeId = planeId;
        this.health = health;
        this.attack = attack;
        this.fuel = fuel;
        this.name = name;
        this.planeAccessory = accessory;
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
