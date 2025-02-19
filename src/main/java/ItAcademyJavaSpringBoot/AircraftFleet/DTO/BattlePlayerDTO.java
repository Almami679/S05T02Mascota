package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
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
