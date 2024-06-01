package be.ucll.examen.mappers.impl;

import be.ucll.examen.domain.dto.RoomDto;
import be.ucll.examen.domain.entities.Room;
import be.ucll.examen.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapperImpl implements Mapper<Room, RoomDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public RoomMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RoomDto toDto(Room room) {
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public Room toEntity(RoomDto roomDto) {
        return modelMapper.map(roomDto, Room.class);
    }
}
