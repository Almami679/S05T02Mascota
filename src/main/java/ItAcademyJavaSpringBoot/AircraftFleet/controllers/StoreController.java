package ItAcademyJavaSpringBoot.AircraftFleet.controllers;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.storeService.storeServiceImpl.StoreService;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
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
import java.util.Map;

@RestController
@RequestMapping("aircraft/store")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Store Controller", description = "Manage plane and accessory purchases")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get available planes for sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available planes for sale")
    })
    @GetMapping("/planes")
    public ResponseEntity<List<Map<String, Object>>> getAvailablePlanes() {
        return ResponseEntity.ok(storeService.getAvailablePlanes());
    }

    @Operation(summary = "Get available accessories for sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available accessories for sale")
    })
    @GetMapping("/accessories")
    public ResponseEntity<List<Map<String, Object>>> getAvailableAccessories() {
        return ResponseEntity.ok(storeService.getAvailableAccessories());
    }

    @Operation(summary = "Buy a plane and add it to the user's hangar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plane successfully purchased and added to hangar"),
            @ApiResponse(responseCode = "400", description = "User does not have enough credits")
    })
    @PostMapping("/buy/plane")
    public ResponseEntity<User> buyPlane(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam PlaneModel model) {
        User user = userService.findUserByName(userDetails.getUsername());
        storeService.buyPlane(user.getId(), model);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Buy and equip an accessory to a plane")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accessory successfully purchased and equipped"),
            @ApiResponse(responseCode = "400", description = "User does not have enough credits or accessory not found")
    })
    @PostMapping("/buy/accessory")
    public ResponseEntity<User> buyAndEquipAccessory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long planeId,
            @RequestParam PlaneAccessoryModel planeAccessory) {

        User user = userService.findUserByName(userDetails.getUsername());
        storeService.buyAndEquipAccessory(user.getId(), planeId, planeAccessory);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Add credits to user's wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credits successfully added to wallet"),
            @ApiResponse(responseCode = "400", description = "Invalid credit amount")
    })
    @PostMapping("/addCredits")
    public ResponseEntity<User> addCredits(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam double amount) {

        User user = userService.findUserByName(userDetails.getUsername());
        return ResponseEntity.ok(userService.addCredits(user.getId(), amount));
    }
}
