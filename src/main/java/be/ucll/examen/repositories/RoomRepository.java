package be.ucll.examen.repositories;

import be.ucll.examen.domain.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    List<Room> findByCampusName(String campusName);

    @Query(value = "SELECT * FROM rooms r " +
            "LEFT JOIN bookings_rooms br on r.id = br.room_id " +
            "LEFT JOIN bookings b on br.booking_id = b.id " +
            "WHERE r.campus_name = :campusName " +
            "AND r.capacity >= :minNumberOfSeats " +
            "AND ((:availableFrom is null AND :availableUntil is null) OR ((:availableFrom < b.time_from AND :availableUntil <= b.time_from) OR (:availableFrom >= b.time_to AND :availableUntil > b.time_to)))",
            nativeQuery = true)
    List<Room> findByQuery(String campusName, LocalDateTime availableFrom, LocalDateTime availableUntil, Integer minNumberOfSeats);
}
