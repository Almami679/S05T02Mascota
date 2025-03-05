package ItAcademyJavaSpringBoot.Macota.IntegrationTests.PlanesService;


import ItAcademyJavaSpringBoot.AircraftFleet.AircraftFleetApplication;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = AircraftFleetApplication.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlanesServiceTestsBuyPlane {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String token = "";

    @BeforeEach
    @DisplayName("Registrar el usuario primero en H2")
    public void register() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new AuthRequestDTO(
                                "albert",
                                "Albert12345!",
                                Role.USER))));

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AuthRequestDTO(
                                        "albert",
                                        "Albert12345!",
                                        Role.USER))))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        token = JsonPath.read(responseBody, "$.token");
    }

    @DisplayName("Compra de avion (creacion en el builder) y recogida en el get")
    @Test
    public void createPlaneAndAddInUser() throws Exception {
        PlaneModel planeTest = PlaneModel.SPITFIRE;
        mockMvc.perform(post("/aircraft/store/buy/plane")
                        .header("Authorization", "Bearer " + token)
                        .param("model", planeTest.name()))
                .andExpect(status().isOk());

        MvcResult userResult = mockMvc.perform(get("/aircraft/hangar/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = userResult.getResponse().getContentAsString();

        assertThat(responseBody).contains(planeTest.getName());
    }
}
