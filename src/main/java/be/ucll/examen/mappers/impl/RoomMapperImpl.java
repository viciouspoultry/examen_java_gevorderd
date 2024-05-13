package be.ucll.examen.mappers.impl;

import be.ucll.examen.domain.dto.RoomDto;
import be.ucll.examen.domain.entities.RoomEntity;
import be.ucll.examen.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapperImpl implements Mapper<RoomEntity, RoomDto> {

    private ModelMapper modelMapper;

    @Autowired
    public RoomMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RoomDto mapTo(RoomEntity roomEntity) {
        return modelMapper.map(roomEntity, RoomDto.class);
    }

    @Override
    public RoomEntity mapFrom(RoomDto roomDto) {
        return modelMapper.map(roomDto, RoomEntity.class);
    }
}
