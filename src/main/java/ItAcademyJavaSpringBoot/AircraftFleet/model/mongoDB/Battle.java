package ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl.BattleService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Getter
@Setter
@Document(collection = "battles")
@Schema(description = "Battle entity representing a battle between two players")
@AllArgsConstructor
@NoArgsConstructor
public class Battle {



    @Id
    @Schema(description = "Battle ID", example = "1")
    @Getter
    private int id;

    @NotNull
    @Schema(description = "Player 1 in the battle")
    private BattlePlayerDTO user1;

    @NotNull
    @Schema(description = "Player 2 in the battle")
    private BattlePlayerDTO user2;

    @Schema(description = "Battle creation date")
    private Date battleDate;

    @Schema(description = "Winner of the battle")
    private BattlePlayerDTO winner;

    public Battle(BattlePlayerDTO player1, BattlePlayerDTO player2) {
        this.user1 = player1;
        this.user2 = player2;
        this.battleDate = new Date(System.currentTimeMillis());
    }
}
