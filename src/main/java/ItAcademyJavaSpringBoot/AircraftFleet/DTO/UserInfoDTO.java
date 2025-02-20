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
public class UserInfoDTO {
    private Long id;
    private String username;
    private int score;
    private List<PlaneDTO> planes;
    private int battlesWon;
    private int battlesLost;

}

