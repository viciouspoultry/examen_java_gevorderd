package be.ucll.examen.services.Impl;

import be.ucll.examen.domain.entities.CampusEntity;
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
    public CampusEntity create(CampusEntity campusToCreate) {
        return campusRepository.save(campusToCreate);
    }

    @Override
    public List<CampusEntity> findAll() {
        return StreamSupport.stream(campusRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public CampusEntity findById(String campusName) {
        return campusRepository.findById(campusName)
                .orElseThrow(() -> new NoSuchElementException("No campus found with id value of '" + campusName + "'."));
    }

    @Override
    public CampusEntity update(String campusName, CampusEntity updatedCampus) {
        CampusEntity campusToUpdate = campusRepository.findById(campusName)
                .orElseThrow(() -> new NoSuchElementException("No campus found with id value of '" + campusName + "'."));
        updatedCampus.setName(campusToUpdate.getName());
        return campusRepository.save(updatedCampus);
    }

    @Override
    public void deleteById(String campusName) {
        CampusEntity campusToDelete = campusRepository.findById(campusName)
                .orElseThrow(() -> new NoSuchElementException("No campus found with id value of '" + campusName + "'."));
        campusRepository.delete(campusToDelete);
    }

    @Override
    public void deleteAll() {
        campusRepository.deleteAll();
    }
}
