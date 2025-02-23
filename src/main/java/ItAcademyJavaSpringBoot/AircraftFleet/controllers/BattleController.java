package ItAcademyJavaSpringBoot.AircraftFleet.controllers;


import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.battleService.battleServiceImpl.BattleService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.mongoDB.Battle;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("aircraft/battles")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Battle Controller", description = "Handles battle operations")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Autowired
    private UserService userService;

    @Operation(summary = "All battles in Database (Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all battles")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Battle>> getAllBattles() {
        return ResponseEntity.ok(battleService.getAllBattles());
    }

    @Operation(summary = "Find an opponent and ask user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opponent found"),
            @ApiResponse(responseCode = "404", description = "No opponents available")
    })
    @GetMapping("/find-opponent")
    public ResponseEntity<BattlePlayerDTO> findOpponent(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findUserByName(userDetails.getUsername());
        BattlePlayerDTO opponentDTO = battleService.findOpponent(user.getId());

        return ResponseEntity.ok(opponentDTO);
    }

    @Operation(summary = "Start a battle with a specific opponent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Battle started successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid battle request")
    })
    @PostMapping("/start/{planeId}")
    public ResponseEntity<Battle> startBattle(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "ID of the user's plane") @PathVariable Long planeId,
            @RequestBody BattlePlayerDTO opponentDTO) {

        User user = userService.findUserByName(userDetails.getUsername());
        Battle battle = battleService.startBattle(user.getId(), planeId, opponentDTO);

        return ResponseEntity.ok(battle);
    }

    @Operation(summary = "All battles for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of battles for the user"),
            @ApiResponse(responseCode = "404", description = "No battles found for the user")
    })
    @GetMapping("/user")
    public ResponseEntity<List<Battle>> getBattlesByUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<Battle> userBattles = battleService.getBattlesByUser(userDetails.getUsername());

        return ResponseEntity.ok(userBattles);
    }
}


