package be.ucll.examen.mappers.impl;

import be.ucll.examen.domain.dto.CampusDto;
import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CampusMapperImpl implements Mapper<Campus, CampusDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public CampusMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CampusDto toDto(Campus campus) {
        return modelMapper.map(campus, CampusDto.class);
    }

    @Override
    public Campus toEntity(CampusDto campusDto) {
        return modelMapper.map(campusDto, Campus.class);
    }
}
