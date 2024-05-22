package be.ucll.examen.services;

import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.domain.entities.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {
    Room create(String campusName, Room room);
    List<Room> findByQuery(String campusName, LocalDateTime availableFrom, LocalDateTime availableUntil, Integer minNumberOfSeats);
    Room findById(String campusName, Long roomId);
    Room findById(Long roomId);
    List<Booking> findBookings(String campusName, Long roomId);
    Room update(String campusName, Long roomId, Room room);
    void deleteById(String campusName, Long roomId);
}
