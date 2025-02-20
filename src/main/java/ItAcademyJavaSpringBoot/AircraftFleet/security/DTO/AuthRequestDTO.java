package ItAcademyJavaSpringBoot.AircraftFleet.security.DTO;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequestDTO {
    @NotBlank(message = "username can't be null")
    @Size(min = 4, max = 20, message = "username have not between 4 & 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "username have a invalid format")
    private String userName;

    @NotBlank(message = "password can't be null")
    @Size(min = 8, message = "password must be at 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "The password must contain uppercase numbers and characters"
    )
    private String password;


    private Role role;
}

