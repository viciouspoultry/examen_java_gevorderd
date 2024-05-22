package be.ucll.examen.controllers;

import be.ucll.examen.domain.dto.BookingDto;
import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.mappers.Mapper;
import be.ucll.examen.services.impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookingController {
    private final BookingServiceImpl bookingService;
    private final Mapper<Booking, BookingDto> bookingMapper;

    @Autowired
    public BookingController(BookingServiceImpl bookingService,
                             Mapper<Booking, BookingDto> bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }


    @PostMapping("/users/{user-id}/bookings")
    public ResponseEntity<BookingDto> createBooking(@PathVariable("user-id") Long userId,
                                                    @RequestBody BookingDto dto) {
        Booking bookingToCreate = bookingMapper.mapFrom(dto);
        BookingDto response = bookingMapper.mapTo(bookingService.create(userId, bookingToCreate));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/users/{user-id}/bookings")
    public ResponseEntity<List<BookingDto>> findAllBookings(@PathVariable("user-id") Long userId) {
        List<BookingDto> response = bookingService.findAll(userId)
                .stream()
                .map(bookingMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{user-id}/bookings/{booking-id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable("user-id") Long userId,
                                                     @PathVariable("booking-id") Long bookingId) {
        BookingDto response = bookingMapper.mapTo(bookingService.findById(userId, bookingId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{user-id}/bookings/{booking-id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable("user-id") Long userId,
                                                    @PathVariable("booking-id") Long bookingId,
                                                    @RequestBody BookingDto dto) {
        Booking updatedBooking = bookingMapper.mapFrom(dto);
        BookingDto response = bookingMapper.mapTo(bookingService.update(userId, bookingId, updatedBooking));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{user-id}/bookings/{booking-id}/rooms/{room-id}")
    public ResponseEntity<BookingDto> addRoomToBooking(@PathVariable("user-id") Long userId,
                                                       @PathVariable("booking-id") Long bookingId,
                                                       @PathVariable("room-id") Long roomId) {
        BookingDto response = bookingMapper.mapTo(bookingService.addRoomToBooking(userId, bookingId, roomId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{user-id}/bookings/{booking-id}")
    public ResponseEntity DeleteBookingById(@PathVariable("user-id") Long userId,
                                        @PathVariable("booking-id") Long bookingId) {
        bookingService.deleteById(userId, bookingId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
