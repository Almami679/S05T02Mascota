package ItAcademyJavaSpringBoot.Macota.IntegrationTests.PlanesService;


import ItAcademyJavaSpringBoot.AircraftFleet.AircraftFleetApplication;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneAccessoriesRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.security.DTO.AuthRequestDTO;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AircraftFleetApplication.class)
@AutoConfigureMockMvc
public class PlaneServiceTestsAddAccessory {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PlaneAccessoriesRepository planeAccessoryRepository;
    @Mock
    private PlaneRepository planeRepository;

    @Autowired

    private final ObjectMapper objectMapper = new ObjectMapper();



    private static String token = "";

    @BeforeEach
    @DisplayName("Registrar el usuario primero en H2")
    public void register() throws Exception {

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
        token = JsonPath.read(responseBody, "$.token");

    }

    @DisplayName("Compra de avion (creacion en el builder) y recogida en el get")
    @Test
    public void addAccessoryInPlane() throws Exception {
        PlaneModel planeTest = PlaneModel.SPITFIRE;
        PlaneAccessoryModel accessoryTest = PlaneAccessoryModel.CANNON_20MM;
        mockMvc.perform(post("/aircraft/store/buy/plane")
                        .header("Authorization", "Bearer " + token)
                        .param("model", planeTest.name()))
                .andExpect(status().isOk());

        MvcResult userResult = mockMvc.perform(get("/aircraft/hangar/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = userResult.getResponse().getContentAsString();

        Integer planeId = JsonPath.read(responseBody, "$.hangar.planes[0].id");
        String planeIdStr = String.valueOf(planeId); // Convertir a String para pasarlo como parámetro

        mockMvc.perform(post("/aircraft/store/buy/accessory")
                        .header("Authorization", "Bearer " + token)
                        .param("planeId", planeIdStr)  // Usar la versión String del ID
                        .param("planeAccessory", accessoryTest.name()))
                .andExpect(status().isOk());

        userResult = mockMvc.perform(get("/aircraft/hangar/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        responseBody = userResult.getResponse().getContentAsString();

        assertThat(responseBody).contains(accessoryTest.getName());
    }


}
