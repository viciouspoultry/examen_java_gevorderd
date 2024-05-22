package be.ucll.examen.services.impl;

import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.repositories.CampusRepository;
import be.ucll.examen.services.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class CampusServiceImpl implements CampusService {
    private final CampusRepository campusRepository;

    @Autowired
    public CampusServiceImpl(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    @Override
    public Campus create(Campus campusToCreate) {
        return campusRepository.save(campusToCreate);
    }

    @Override
    public List<Campus> findAll() {
        return StreamSupport.stream(campusRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Campus findById(String campusName) {
        return campusRepository.findById(campusName)
                .orElseThrow(() -> new NoSuchElementException("No campus found with id value of '" + campusName + "'."));
    }

    @Override
    public Campus update(String campusName, Campus updatedCampus) {
        Campus campusToUpdate = campusRepository.findById(campusName)
                .orElseThrow(() -> new NoSuchElementException("No campus found with id value of '" + campusName + "'."));
        updatedCampus.setName(campusToUpdate.getName());
        return campusRepository.save(updatedCampus);
    }

    @Override
    public void deleteById(String campusName) {
        Campus campusToDelete = campusRepository.findById(campusName)
                .orElseThrow(() -> new NoSuchElementException("No campus found with id value of '" + campusName + "'."));
        campusRepository.delete(campusToDelete);
    }

    @Override
    public void deleteAll() {
        campusRepository.deleteAll();
    }
}
