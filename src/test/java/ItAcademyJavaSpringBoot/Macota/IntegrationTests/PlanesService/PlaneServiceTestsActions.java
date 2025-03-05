package ItAcademyJavaSpringBoot.Macota.IntegrationTests.PlanesService;


import ItAcademyJavaSpringBoot.AircraftFleet.AircraftFleetApplication;
import ItAcademyJavaSpringBoot.AircraftFleet.Services.planeService.planeServiceImpl.PlaneService;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAccessoryModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneAction;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.PlaneModel;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Role;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.Plane;
import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneAccessoriesRepository;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.PlaneRepository;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AircraftFleetApplication.class)
@AutoConfigureMockMvc
public class PlaneServiceTestsActions {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaneAccessoriesRepository planeAccessoryRepository;

    @Mock
    private PlaneRepository planeRepository;

    @Mock
    private PlaneService planeService;  // ✅ Mockeamos PlaneService

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String token = "";
    private static Plane mockPlane = null;

    @BeforeEach
    void setup() throws Exception {

        // Crear avión simulado
        mockPlane = new Plane();
        mockPlane.setId(1L);
        mockPlane.setModel(PlaneModel.SPITFIRE.getName());
        mockPlane.setFuel(20);
        mockPlane.setHealth(50);

        // Mockear repositorio para que devuelva el avión cuando se lo busque
        Mockito.when(planeRepository.findById(1L)).thenReturn(Optional.of(mockPlane));
         // Asegura que el objeto no sea null

        // Simular login para obtener token
        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AuthRequestDTO("albertTest", "Albert12345!", Role.USER))))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        token = JsonPath.read(responseBody, "$.token");
    }
    @Test
    @DisplayName("Verificar que un avión con poca vida y combustible puede repostar")
    void testRefuelPlane() throws Exception {
        assertNotNull(mockPlane, "El mockPlane debería haber sido inicializado en @BeforeEach");

        mockMvc.perform(put("/aircraft/hangar/update-plane/{planeId}", 1L)  // ✅ Cambiar a PUT
                        .header("Authorization", "Bearer " + token)
                        .param("action", PlaneAction.REFUEL.name()))
                .andExpect(status().isOk());


        MvcResult userResult = mockMvc.perform(get("/aircraft/hangar/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = userResult.getResponse().getContentAsString();

        // ✅ Verificamos que el fuel se haya actualizado correctamente
        assertThat(responseBody).contains("\"fuel\":100");
    }
}


