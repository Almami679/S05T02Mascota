package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaneDTO {
    private Long id;
    private String name;
    private String model;
    private int baseHealth;
    private int baseAttack;
    private int fuel;
    private List<PlaneAccessoryDTO> accessories;

    public int getTotalHealth() {
        int bonusHealth = accessories.stream()
                .filter(a -> "Health_Boost".equalsIgnoreCase(a.getType()))
                .mapToInt(PlaneAccessoryDTO::getBonus)
                .sum();
        return baseHealth + bonusHealth;
    }

    public int getTotalAttack() {
        int bonusAttack = accessories.stream()
                .filter(a -> "Attack_Boost".equalsIgnoreCase(a.getType()))
                .mapToInt(PlaneAccessoryDTO::getBonus)
                .sum();
        return baseAttack + bonusAttack;
    }
}
