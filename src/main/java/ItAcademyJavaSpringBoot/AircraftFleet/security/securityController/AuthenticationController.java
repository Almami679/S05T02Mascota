package ItAcademyJavaSpringBoot.AircraftFleet.security.securityController;

import ItAcademyJavaSpringBoot.AircraftFleet.Services.UserService.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.exceptions.UsernameIsInUseException;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthResponseDTO;
import ItAcademyJavaSpringBoot.AircraftFleet.security.securityService.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;


//    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
//        this.authenticationManager = authenticationManager;
//        this.userService = userService;
//        this.jwtService = jwtService;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid AuthRequestDTO request) {
        if (userService.userIsPresent(request.getUserName())) {
            throw new UsernameIsInUseException("El usuario ya existe");
        } else {
            request.setPassword(jwtService.passwordEncoder(request.getPassword()));
            User newUser = userService.addNewUser(request);
            return ResponseEntity.ok(newUser);
        }
    }

    @Operation(summary = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );

        User user = userService.findUserByName(request.getUserName());
        if (user == null) {
           throw new UsernameNotFoundException("User " + request.getUserName() + "not found");
        } else {
            String token = jwtService.generateToken(user.getUserName(), user.getRole());
            return ResponseEntity.ok(new AuthResponseDTO(token, user.getUserName(), user.getRole()));
        }
    }
}


