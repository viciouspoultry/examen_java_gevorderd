package be.ucll.examen.controllers;

import be.ucll.examen.TestDataUtil;
import be.ucll.examen.domain.entities.CampusEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CampusControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public CampusControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateCampusReturnsHttp201Created() throws Exception {
        CampusEntity testCampus = TestDataUtil.createTestCampusA();
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
    public void testThatCreateCampusReturnsSavedCampus() throws Exception {
        CampusEntity testCampus = TestDataUtil.createTestCampusA();
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
}
