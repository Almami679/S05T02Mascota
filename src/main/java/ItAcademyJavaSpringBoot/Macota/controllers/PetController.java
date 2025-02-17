package ItAcademyJavaSpringBoot.Macota.controllers;

import ItAcademyJavaSpringBoot.Macota.Services.PetService;
import ItAcademyJavaSpringBoot.Macota.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;


    @Operation(summary = "Test SWAGGER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Error")
    })
    @GetMapping("/test")
    public ResponseEntity<String> test(@Valid @RequestBody String name ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("This is a test " + name);
    }


}
