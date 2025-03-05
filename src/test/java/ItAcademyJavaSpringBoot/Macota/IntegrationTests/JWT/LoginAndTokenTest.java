package ItAcademyJavaSpringBoot.Macota.IntegrationTests.JWT;

import ItAcademyJavaSpringBoot.AircraftFleet.AircraftFleetApplication;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AircraftFleetApplication.class)
@AutoConfigureMockMvc
public class LoginAndTokenTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Autowired

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    @DisplayName("Registrar el usuario primero en H2")
    public void register() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new AuthRequestDTO(
                                "albertTest",
                                "Albert12345!",
                                Role.USER))));
    }

    @DisplayName("Test de autenticación y acceso a path protegido")
    @Test
    public void loginAndAccessProtectedRoute() throws Exception {



        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AuthRequestDTO(
                                        "albertTest",
                                        "Albert12345!",
                                        Role.USER))))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.token");

        System.out.println("[TOKEN: " + token + "]");



        mockMvc.perform(get("/aircraft/hangar/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

}

