package ItAcademyJavaSpringBoot.AircraftFleet.controllers;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService.HangarService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.mainService.MainService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircraft")
public class HangarController {

    @Autowired
    private MainService mainService;

    @Autowired
    private HangarService hangarService;


    @Operation(summary = "Get all planes owned by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of owned planes")
    })
    @GetMapping("/my-planes")
    public ResponseEntity<List<Plane>> getUserPlanes(@AuthenticationPrincipal UserDetails userDetails) {
        List<Plane> planes = hangarService.getAllPlanesForUser(userDetails.getUsername());
        return ResponseEntity.ok(planes);
    }


}
