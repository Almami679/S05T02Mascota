package ItAcademyJavaSpringBoot.AircraftFleet.controllers;

import ItAcademyJavaSpringBoot.AircraftFleet.DTO.BattlePlayerDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.DTOConstructors;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.hangarServiceImpl.HangarService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.NoHavePermissionsException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("aircraft/hangar")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Hangar Controller", description = "Manage user's hangar and planes")
public class HangarController {

    @Autowired
    private HangarService hangarService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaneService planeService;

    @Autowired
    private DTOConstructors dtoConstructors;

    @Operation(summary = "Get all planes in the user's hangar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of planes in the hangar"),
            @ApiResponse(responseCode = "404", description = "Hangar not found for user")
    })
    @GetMapping("/planes")
    public ResponseEntity<Hangar> getHangarPlanes(@AuthenticationPrincipal UserDetails userDetails) {

        Hangar hangar = hangarService.getAllPlanesForUser(userDetails.getUsername());

        return ResponseEntity.ok(hangar);
    }

    @Operation(summary = "Update hangar state (time of day & weather)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hangar state updated successfully"),
            @ApiResponse(responseCode = "404", description = "User or Hangar not found")
    })
    @PutMapping("/update-state")
    public ResponseEntity<Hangar> updateHangarState(@AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findUserByName(userDetails.getUsername());
        Hangar updatedHangar = hangarService.updateHangarState(user.getId());

        return ResponseEntity.ok(updatedHangar);
    }

    @Operation(summary = "Update Plane in Hangar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hangar state updated successfully"),
            @ApiResponse(responseCode = "404", description = "User or Hangar not found")
    })
    @PutMapping("/update-plane/{planeId}")
    public ResponseEntity<Plane> updatePlaneInHangar(@AuthenticationPrincipal UserDetails userDetails,
                                                      @Parameter(description = "ID of the user's plane")
                                                      @PathVariable Long planeId,
                                                      @RequestParam PlaneAction action) {

        User user = userService.findUserByName(userDetails.getUsername());
        Plane updatedPlane = planeService.updatePlaneStats(planeId, action);

        return ResponseEntity.ok(updatedPlane);
    }

    @Operation(summary = "Get all Planes (ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hangar state updated successfully"),
            @ApiResponse(responseCode = "404", description = "User or Hangar not found")
    })
    @GetMapping("/AllPlanes")
    public ResponseEntity<List<BattlePlayerDTO>> getAllPlanes(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByName(userDetails.getUsername());
        if(!user.getRole().equals(Role.ADMIN)){
            throw new NoHavePermissionsException("You don't have ADMIN permissions");
        }
        List<BattlePlayerDTO> planes = dtoConstructors.getDTOForAllPlanes(hangarService.getAllHangars());

        return ResponseEntity.ok(planes);

    }

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User detected"),
            @ApiResponse(responseCode = "404", description = "User or Hangar not found")
    })
    @GetMapping("/user")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findUserByName(userDetails.getUsername());
        user.getHangar().updateHangarState();
        return ResponseEntity.ok(user);
    }


}
