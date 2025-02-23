package ItAcademyJavaSpringBoot.AircraftFleet.controllers;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.hangarService.hangarServiceImpl.HangarService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Hangar;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all planes in the user's hangar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of planes in the hangar"),
            @ApiResponse(responseCode = "404", description = "Hangar not found for user")
    })
    @GetMapping("/planes")
    public ResponseEntity<List<Plane>> getHangarPlanes(@AuthenticationPrincipal UserDetails userDetails) {

        List<Plane> planes = hangarService.getAllPlanesForUser(userDetails.getUsername());

        return ResponseEntity.ok(planes);
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
}
