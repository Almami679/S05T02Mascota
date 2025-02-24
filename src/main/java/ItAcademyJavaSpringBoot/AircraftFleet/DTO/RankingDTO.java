package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RankingDTO {

    private String username;
    private double score;
    private int level;

    public RankingDTO(String username, double score){
        this.username = username;
        this.score = score;
        this.level = calculateLevel();
    }

    private int calculateLevel(){
        int level = 0;
        if (score < 500) {
            level = 1;
        } else if (score < 1000) {
            level = 2;
        } else if (score < 2000) {
            level = 3;
        } else if (score < 3000){
            level = 4;
        } else if (score < 5000) {
            level = 5;
        }
        return level;
    }

    public void setLevel(){
        this.level = calculateLevel();
    }

}
