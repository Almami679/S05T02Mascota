package ItAcademyJavaSpringBoot.AircraftFleet.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
}
