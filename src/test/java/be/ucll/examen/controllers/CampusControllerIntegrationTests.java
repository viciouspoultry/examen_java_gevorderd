package be.ucll.examen.controllers;

import be.ucll.examen.TestDataUtil;
import be.ucll.examen.domain.dto.CampusDto;
import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.services.impl.CampusServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CampusControllerIntegrationTests {

    private CampusServiceImpl campusService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public CampusControllerIntegrationTests(MockMvc mockMvc,
                                            ObjectMapper objectMapper,
                                            CampusServiceImpl campusService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.campusService = campusService;
    }

    @Test
    public void createCampus_returnsHttpStatus201Created() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        String campusJson = objectMapper.writeValueAsString(testCampus);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/campus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(campusJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void createCampus_returnsSavedCampus() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        String campusJson = objectMapper.writeValueAsString(testCampus);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/campus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(campusJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TestNameA")
        );
    }

    @Test
    public void findAllCampuses_returnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/campus")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findAllCampuses_returnsListOfCampuses() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        campusService.create(testCampus);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/campus")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("TestNameA")
        );
    }

    @Test
    public void findCampusById_returnsHttpStatus200_campusExists() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        campusService.create(testCampus);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/campus/TestNameA")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findCampusById_returnsHttpStatus404_campusDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/campus/TestNameA")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findCampusById_returnsCampus_campusExists() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        campusService.create(testCampus);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/campus/TestNameA")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TestNameA")
        );
    }

    @Test
    public void updateCampus_returnsHttpStatus200_CampusExists() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        Campus savedCampus = campusService.create(testCampus);

        CampusDto testCampusDto = TestDataUtil.createTestCampusDtoA();
        String campusDtoJson = objectMapper.writeValueAsString(testCampusDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/campus/" + savedCampus.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(campusDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateCampus_returnsHttpStatus404_campusDoesNotExist() throws Exception {
        CampusDto testCampusDto = TestDataUtil.createTestCampusDtoA();
        String campusDtoJson = objectMapper.writeValueAsString(testCampusDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/campus/TestNameA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(campusDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void fullUpdate_updatesExistingCampus() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        Campus savedCampus = campusService.create(testCampus);
        CampusDto testCampusDto = TestDataUtil.createTestCampusDtoB();

        String campusDtoJson = objectMapper.writeValueAsString(testCampusDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/campus/" + savedCampus.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(campusDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TestNameA")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.address").value("TestAddressB")
        );
    }

    @Test
    public void deleteCampusById_returnsHttpStatus404_campusDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/campus/TestNameA")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void deleteCampusById_returnsHttpStatus204_CampusExists() throws Exception {
        Campus testCampus = TestDataUtil.createTestCampusA();
        Campus savedCampus = campusService.create(testCampus);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/campus/" + savedCampus.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
