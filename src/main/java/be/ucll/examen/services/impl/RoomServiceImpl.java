package be.ucll.examen.services.impl;

import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.domain.entities.Room;
import be.ucll.examen.repositories.RoomRepository;
import be.ucll.examen.services.RoomService;
import be.ucll.examen.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final CampusServiceImpl campusService;
    private final EntityManager entityManager;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
                           CampusServiceImpl campusService,
                           EntityManager entityManager) {
        this.roomRepository = roomRepository;
        this.campusService = campusService;
        this.entityManager = entityManager;
    }

    @Override
    public Room create(String campusName, Room roomToCreate) {
        Campus foundCampus = campusService.findById(campusName);

        // CONSTRAINT: Binnen een campus-gebouw mag je geen 2 lokalen hebben met dezelfde naam.
        Validator.roomNameDoesNotExist(foundCampus, roomToCreate.getName());

        roomToCreate.setCampus(foundCampus);
        return roomRepository.save(roomToCreate);
    }

    @Override
    public List<Room> findByQuery(String campusName, LocalDateTime availableFrom, LocalDateTime availableUntil, Integer minNumberOfSeats) {
        // CONSTRAINT: Startuur moet voor het einduur liggen.
        Validator.startTimeIsBeforeEndTime(availableFrom, availableUntil);

        // CONSTRAINT: availableFrom and availableUntil parameters must both be null OR must both be not null.
        if (availableFrom == null && availableUntil != null) {
            throw new IllegalArgumentException("The start time query parameter is missing.");
        }
        if(availableFrom != null && availableUntil == null) {
            throw new IllegalArgumentException("The end time query parameter is missing.");
        }
        Campus foundCampus = campusService.findById(campusName);
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
//        Root<Room> room = cq.from(Room.class);
//        Join<Booking, Room> bookingRoomJoin = room.join("bookedBy");
//        Predicate predicateForCampusName = cb.equal(room.get("campus"), foundCampus.getName());
//        Predicate predicateForMinNumberOfSeats = cb.greaterThanOrEqualTo(room.get("capacity"), minNumberOfSeats);

        return roomRepository.findByQuery(foundCampus.getName(), availableFrom, availableUntil, minNumberOfSeats);
    }

    @Override
    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("No room found with id value of " + roomId + "."));
    }

    @Override
    public Room findById(String campusName, Long roomId) {
        Campus foundCampus = campusService.findById(campusName);
        return roomRepository
                .findByCampusName(foundCampus.getName())
                .stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No room found with id value of " + roomId + "."));
    }

    @Override
    public List<Booking> findBookings(String campusName, Long roomId) {
        Room foundRoom = this.findById(campusName, roomId);
        return foundRoom
                .getBookedBy()
                .stream()
                .toList();
    }

    @Override
    public Room update(String campusName, Long roomId, Room updatedRoom) {
        Room roomToUpdate = this.findById(campusName, roomId);
        updatedRoom.setId(roomToUpdate.getId());
        updatedRoom.setCampus(campusService.findById(campusName));
        return roomRepository.save(updatedRoom);
    }

    @Override
    public void deleteById(String campusName, Long roomId) {
        Campus campusToUpdate = campusService.findById(campusName);
        Room roomToDelete = this.findById(campusName, roomId);
        campusToUpdate.getRooms().remove(roomToDelete);
        campusService.update(campusName, campusToUpdate);
        roomRepository.delete(roomToDelete);
    }
}



