package ItAcademyJavaSpringBoot.AircraftFleet.controllers;


import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.BattleService.BattleService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/battles")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Operation(summary = "Get all battles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Battles retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Battle.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<Battle>> getAllBattles() {
        return ResponseEntity.ok(battleService.getAllBattles());
    }

    @Operation(summary = "Get a battle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Battle found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Battle.class))),
            @ApiResponse(responseCode = "404", description = "Battle not found")
    })
    @GetMapping("/get{id}")
    public ResponseEntity<Battle> getBattleById(@PathVariable Long id) {
        return ResponseEntity.ok(battleService.getBattle(id));
    }

    @Operation(summary = "Create a new battle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Battle created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Battle.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/fight")
    public ResponseEntity<Battle> createBattle(@RequestBody BattlePlayerDTO playerDTO) {
        Battle newBattle = battleService.createNewBattle(playerDTO);
        return ResponseEntity.ok(battleService.addBattle(newBattle));

    }

}

