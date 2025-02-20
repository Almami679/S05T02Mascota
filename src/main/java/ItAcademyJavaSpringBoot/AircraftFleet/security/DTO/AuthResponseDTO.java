package ItAcademyJavaSpringBoot.AircraftFleet.security.DTO;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String username;
    private Role role;
}
