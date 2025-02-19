package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaneAccessoryDTO {
    private String name;
    private String type;
    private int bonus;
}
