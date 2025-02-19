package ItAcademyJavaSpringBoot.AircraftFleet.security.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    private String userName;
    private String password;
}

