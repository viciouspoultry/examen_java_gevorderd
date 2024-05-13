package be.ucll.examen.services;

import be.ucll.examen.domain.entities.BookingEntity;
import be.ucll.examen.domain.entities.RoomEntity;

import java.util.Date;
import java.util.List;

public interface RoomService {
    RoomEntity create(String campusName, RoomEntity room);
    List<RoomEntity> findByQuery(String campusName, Date availableFrom, Date availableUntil, Integer minNumberOfSeats);
    RoomEntity findById(String campusName, Long roomId);
    RoomEntity findById(Long roomId);
    List<BookingEntity> findBookingsAtRoom(String campusName, Long roomId);
    RoomEntity update(String campusName, Long roomId, RoomEntity room);
    void deleteById(String campusName, Long roomId);
}
