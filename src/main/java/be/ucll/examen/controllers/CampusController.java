package be.ucll.examen.controllers;

import be.ucll.examen.domain.dto.CampusDto;
import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.mappers.Mapper;
import be.ucll.examen.services.impl.CampusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CampusController {
    private final CampusServiceImpl campusService;
    private final Mapper<Campus, CampusDto> campusMapper;

    @Autowired
    public CampusController(CampusServiceImpl campusService,
                            Mapper<Campus, CampusDto> campusMapper) {
        this.campusService = campusService;
        this.campusMapper = campusMapper;
    }


    @PostMapping("/campus")
    public ResponseEntity<CampusDto> createCampus(@RequestBody CampusDto dto) {
        Campus campusToCreate = campusMapper.mapFrom(dto);
        CampusDto response = campusMapper.mapTo(campusService.create(campusToCreate));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/campus")
    public ResponseEntity<List<CampusDto>> findAllCampuses() {
        List<CampusDto> response = campusService.findAll()
                .stream()
                .map(campusMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/campus/{campus-id}")
    public ResponseEntity<CampusDto> findCampusById(@PathVariable("campus-id") String campusName) {
        CampusDto response = campusMapper.mapTo(campusService.findById(campusName));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/campus/{campus-id}")
    public ResponseEntity<CampusDto> updateCampus(@PathVariable("campus-id") String campusName,
                                                  @RequestBody CampusDto dto) {
        Campus updatedCampus = campusMapper.mapFrom(dto);
        CampusDto response = campusMapper.mapTo(campusService.update(campusName, updatedCampus));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/campus/{campus-id}")
    public ResponseEntity deleteCampusById(@PathVariable("campus-id") String campusName) {
        campusService.deleteById(campusName);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/campus")
    public ResponseEntity deleteAllCampuses() {
        campusService.deleteAll();
        return ResponseEntity
                .noContent()
                .build();
    }
}
