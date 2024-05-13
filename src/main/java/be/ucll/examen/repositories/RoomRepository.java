package be.ucll.examen.repositories;

import be.ucll.examen.domain.entities.RoomEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
    List<RoomEntity> findByCampusName(String campusName);

    @Query(value = "SELECT * FROM rooms r " +
            "LEFT JOIN bookings_rooms br on r.id = br.room_id " +
            "LEFT JOIN bookings b on br.booking_id = b.id " +
            "WHERE r.campus_name = :campusName " +
            "AND (:minNumberOfSeats is null OR r.capacity >= :minNumberOfSeats) " +
            "AND ((:availableFrom is null AND :availableUntil is null) OR ((b.time_from < :availableFrom AND b.time_to <= :availableFrom) OR (b.time_from >= :availableUntil AND b.time_to > :availableUntil)))",
            nativeQuery = true)
    List<RoomEntity> findByQuery(String campusName, Date availableFrom, Date availableUntil, Integer minNumberOfSeats);
}
