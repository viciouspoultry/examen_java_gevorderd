package be.ucll.examen.mappers.impl;

import be.ucll.examen.domain.dto.BookingDto;
import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperImpl implements Mapper<Booking, BookingDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public BookingMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingDto toDto(Booking booking) {
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public Booking toEntity(BookingDto bookingDto) {
        return modelMapper.map(bookingDto, Booking.class);
    }
}
