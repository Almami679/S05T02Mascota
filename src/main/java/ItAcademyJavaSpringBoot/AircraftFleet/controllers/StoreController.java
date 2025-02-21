package ItAcademyJavaSpringBoot.AircraftFleet.controllers;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService.StoreService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aircraft/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Operation(summary = "Get available planes for sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available planes for sale")
    })
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAvailablePlanes() {
        List<Map<String, Object>> planesForSale = storeService.getAvailablePlanes();
        return ResponseEntity.ok(planesForSale);
    }

    @Operation(summary = "Buy a plane from the store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plane purchased successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid request")
    })
    @PostMapping("/buy")
    public ResponseEntity<Plane> buyPlane(@AuthenticationPrincipal User user,
                                          @RequestParam PlaneModel plane) {
        Plane purchasedPlane = storeService.buyPlane(user, plane);
        return ResponseEntity.ok(purchasedPlane);
    }

    @Operation(summary = "Add credits for the store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plane purchased successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid request")
    })
    @PostMapping("/addCredits")
    public ResponseEntity<User> buyPlane(@AuthenticationPrincipal User user,
                                          @RequestParam double credits) {
        return ResponseEntity.ok(storeService.addCredits(user,credits));
    }
}
