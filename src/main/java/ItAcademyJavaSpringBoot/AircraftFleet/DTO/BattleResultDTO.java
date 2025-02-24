package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class BattleResultDTO {

    private boolean winner;
    private double score;
    private PlaneDTO planeOpponent;
    private Date battleDate;
}
