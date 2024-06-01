package be.ucll.examen.services.impl;

import be.ucll.examen.TestDataUtil;
import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.repositories.CampusRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CampusServiceImplTests {
    @InjectMocks
    private CampusServiceImpl underTest;
    @Mock
    private CampusRepository campusRepository;


    @Test
    public void createMethod_SavesCampus() {
        Campus campus = TestDataUtil.createTestCampusA();

        when(campusRepository.save(campus)).thenReturn(campus);

        Campus response = underTest.create(campus);

        assertEquals(campus.getName(), response.getName());

        verify(campusRepository, times(1)).save(campus);
    }
}
