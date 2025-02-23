package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class BattlePlayerDTO {
    @Setter
    private Long userId;
    @Setter
    private String username;
    @Setter
    private PlaneDTO plane;
    @Setter
    private double score;
}
