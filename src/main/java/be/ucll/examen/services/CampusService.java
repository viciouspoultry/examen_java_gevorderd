package be.ucll.examen.services;

import be.ucll.examen.domain.entities.CampusEntity;

import java.util.List;

public interface CampusService {
    CampusEntity create(CampusEntity campus);
    List<CampusEntity> findAll();
    CampusEntity findById(String campusName);
    CampusEntity update(String campusName, CampusEntity campus);
    void deleteById(String campusName);
    void deleteAll();
}
