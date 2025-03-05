package ItAcademyJavaSpringBoot.Macota.IntegrationTests.JWT;

import ItAcademyJavaSpringBoot.AircraftFleet.AircraftFleetApplication;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.userService.userServiceImpl.UserService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AircraftFleetApplication.class)
@AutoConfigureMockMvc
class RegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Test con formato de contraseña incorrecto")
    @Test
    public void registerAndVerificationTest1() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AuthRequestDTO(
                                        "albertTest",
                                        "albert12345",
                                        Role.USER))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Formato de contraseña incorrecto"));
    }


    @DisplayName("Test de registro con contraseña correcta")
    @Test
    public void registerAndVerificationTest2() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AuthRequestDTO(
                                        "albertTest",
                                        "Albert12345!",
                                        Role.USER))))
                .andExpect(status().isOk());
    }
}








