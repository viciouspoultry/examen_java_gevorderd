package be.ucll.examen.controllers;

import be.ucll.examen.domain.dto.BookingDto;
import be.ucll.examen.domain.dto.RoomDto;
import be.ucll.examen.domain.entities.BookingEntity;
import be.ucll.examen.domain.entities.RoomEntity;
import be.ucll.examen.mappers.Mapper;
import be.ucll.examen.services.Impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class RoomController {
    private final RoomServiceImpl roomService;
    private final Mapper<RoomEntity, RoomDto> roomMapper;
    private final Mapper<BookingEntity, BookingDto> bookingMapper;

    @Autowired
    public RoomController(RoomServiceImpl roomService,
                          Mapper<RoomEntity, RoomDto> roomMapper,
                          Mapper<BookingEntity, BookingDto> bookingMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
        this.bookingMapper = bookingMapper;
    }


    @PostMapping("/campus/{campus-id}/rooms")
    public ResponseEntity<RoomDto> createRoom(@PathVariable("campus-id") String campusName,
                                              @RequestBody RoomDto dto) {
        RoomEntity roomToCreate = roomMapper.mapFrom(dto);
        RoomDto response = roomMapper.mapTo(roomService.create(campusName, roomToCreate));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/campus/{campus-id}/rooms")
    public ResponseEntity<List<RoomDto>> findRooms(@PathVariable("campus-id") String campusName,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date availableFrom,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date availableUntil,
                                                     @RequestParam(required = false) Integer minNumberOfSeats) {
        List<RoomDto> response = roomService
                .findByQuery(campusName, availableFrom, availableUntil, minNumberOfSeats)
                .stream()
                .map(roomMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/campus/{campus-id}/rooms/{room-id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable("campus-id") String campusName,
                                               @PathVariable("room-id") Long roomId) {
        RoomDto response = roomMapper.mapTo(roomService.findById(campusName, roomId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/campus/{campus-id}/rooms/{room-id}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsAtRoom(@PathVariable("campus-id") String campusName,
                                                                 @PathVariable("room-id") Long roomId) {
        List<BookingDto> response = roomService
                .findBookingsAtRoom(campusName, roomId)
                .stream()
                .map(bookingMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/campus/{campus-id}/rooms/{room-id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("campus-id") String campusName,
                                              @PathVariable("room-id") Long roomId,
                                              @RequestBody RoomDto dto) {
        RoomEntity updatedRoom = roomMapper.mapFrom(dto);
        RoomDto response = roomMapper.mapTo(roomService.update(campusName, roomId, updatedRoom));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/campus/{campus-id}/rooms/{room-id}")
    public ResponseEntity deleteRoom(@PathVariable("campus-id") String campusName,
                                     @PathVariable("room-id") Long roomId) {
        roomService.deleteById(campusName, roomId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
