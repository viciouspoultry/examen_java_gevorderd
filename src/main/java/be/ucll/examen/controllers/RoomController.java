package be.ucll.examen.controllers;

import be.ucll.examen.domain.dto.BookingDto;
import be.ucll.examen.domain.dto.RoomDto;
import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.domain.entities.Room;
import be.ucll.examen.mappers.Mapper;
import be.ucll.examen.services.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class RoomController {
    private final RoomServiceImpl roomService;
    private final Mapper<Room, RoomDto> roomMapper;
    private final Mapper<Booking, BookingDto> bookingMapper;

    @Autowired
    public RoomController(RoomServiceImpl roomService,
                          Mapper<Room, RoomDto> roomMapper,
                          Mapper<Booking, BookingDto> bookingMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
        this.bookingMapper = bookingMapper;
    }


    @PostMapping("/campus/{campus-id}/rooms")
    public ResponseEntity<RoomDto> createRoom(@PathVariable("campus-id") String campusName,
                                              @RequestBody RoomDto dto) {
        Room roomToCreate = roomMapper.toEntity(dto);
        RoomDto response = roomMapper.toDto(roomService.create(campusName, roomToCreate));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/campus/{campus-id}/rooms")
    public ResponseEntity<List<RoomDto>> findRooms(@PathVariable("campus-id") String campusName,
                                                   @RequestParam(value = "availableFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime availableFrom,
                                                   @RequestParam(value = "availableUntil", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime availableUntil,
                                                   @RequestParam(defaultValue = "0") Integer minNumberOfSeats) {
        List<RoomDto> response = roomService
                .findByQuery(campusName, availableFrom, availableUntil, minNumberOfSeats)
                .stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/campus/{campus-id}/rooms/{room-id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable("campus-id") String campusName,
                                               @PathVariable("room-id") Long roomId) {
        RoomDto response = roomMapper.toDto(roomService.findById(campusName, roomId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/campus/{campus-id}/rooms/{room-id}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsAtRoom(@PathVariable("campus-id") String campusName,
                                                                 @PathVariable("room-id") Long roomId) {
        List<BookingDto> response = roomService
                .findBookings(campusName, roomId)
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/campus/{campus-id}/rooms/{room-id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("campus-id") String campusName,
                                              @PathVariable("room-id") Long roomId,
                                              @RequestBody RoomDto dto) {
        Room updatedRoom = roomMapper.toEntity(dto);
        RoomDto response = roomMapper.toDto(roomService.update(campusName, roomId, updatedRoom));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/campus/{campus-id}/rooms/{room-id}")
    public ResponseEntity deleteRoomById(@PathVariable("campus-id") String campusName,
                                         @PathVariable("room-id") Long roomId) {
        roomService.deleteById(campusName, roomId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
