package be.ucll.examen.services;

import be.ucll.examen.domain.entities.Campus;

import java.util.List;

public interface CampusService {
    Campus create(Campus campus);
    List<Campus> findAll();
    Campus findById(String campusName);
    Campus update(String campusName, Campus campus);
    void deleteById(String campusName);
    void deleteAll();
}
