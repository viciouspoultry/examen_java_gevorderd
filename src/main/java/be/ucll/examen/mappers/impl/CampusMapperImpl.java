package be.ucll.examen.mappers.impl;

import be.ucll.examen.domain.dto.CampusDto;
import be.ucll.examen.domain.entities.CampusEntity;
import be.ucll.examen.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CampusMapperImpl implements Mapper<CampusEntity, CampusDto> {
    private ModelMapper modelMapper;

    @Autowired
    public CampusMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CampusDto mapTo(CampusEntity campusEntity) {
        return modelMapper.map(campusEntity, CampusDto.class);
    }

    @Override
    public CampusEntity mapFrom(CampusDto campusDto) {
        return modelMapper.map(campusDto, CampusEntity.class);
    }
}
