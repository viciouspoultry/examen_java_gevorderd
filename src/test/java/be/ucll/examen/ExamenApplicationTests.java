package be.ucll.examen;

import be.ucll.examen.controllers.CampusController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExamenApplicationTests {

	@Autowired
	CampusController campusController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(campusController);
	}
}
