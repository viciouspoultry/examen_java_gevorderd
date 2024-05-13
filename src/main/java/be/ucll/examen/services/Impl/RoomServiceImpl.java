package be.ucll.examen.services.Impl;

import be.ucll.examen.domain.entities.BookingEntity;
import be.ucll.examen.domain.entities.CampusEntity;
import be.ucll.examen.domain.entities.RoomEntity;
import be.ucll.examen.repositories.RoomRepository;
import be.ucll.examen.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final CampusServiceImpl campusService;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
                           CampusServiceImpl campusService) {
        this.roomRepository = roomRepository;
        this.campusService = campusService;
    }

    @Override
    public RoomEntity create(String campusName, RoomEntity roomToCreate) {
        CampusEntity foundCampus = campusService.findById(campusName);

        // CONSTRAINT: Binnen een campus-gebouw mag je geen 2 lokalen hebben met dezelfde naam.
        checkIfRoomNameExists(foundCampus, roomToCreate.getName());

        roomToCreate.setCampus(foundCampus);
        return roomRepository.save(roomToCreate);
    }

    @Override
    public List<RoomEntity> findByQuery(String campusName, Date availableFrom, Date availableUntil, Integer minNumberOfSeats) {
        CampusEntity foundCampus = campusService.findById(campusName);
        return roomRepository.findByQuery(foundCampus.getName(), availableFrom, availableUntil, minNumberOfSeats);
    }

    @Override
    public RoomEntity findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("No room found with id value of " + roomId + "."));
    }

    @Override
    public RoomEntity findById(String campusName, Long roomId) {
        CampusEntity foundCampus = campusService.findById(campusName);
        return roomRepository
                .findByCampusName(foundCampus.getName())
                .stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No room found with id value of " + roomId + "."));
    }

    @Override
    public List<BookingEntity> findBookingsAtRoom(String campusName, Long roomId) {
        RoomEntity foundRoom = this.findById(campusName, roomId);
        return foundRoom
                .getBookedBy()
                .stream()
                .toList();
    }

    @Override
    public RoomEntity update(String campusName, Long roomId, RoomEntity updatedRoom) {
        RoomEntity roomToUpdate = this.findById(campusName, roomId);
        updatedRoom.setId(roomToUpdate.getId());
        updatedRoom.setCampus(campusService.findById(campusName));
        return roomRepository.save(updatedRoom);
    }

    @Override
    public void deleteById(String campusName, Long roomId) {
        CampusEntity campusToUpdate = campusService.findById(campusName);
        RoomEntity roomToDelete = this.findById(campusName, roomId);
        campusToUpdate.getRooms().remove(roomToDelete);
        campusService.update(campusName, campusToUpdate);
        roomRepository.delete(roomToDelete);
    }

    public void checkIfRoomNameExists(CampusEntity campus, String roomName) {
        List<RoomEntity> roomsFilteredByName = campus.getRooms()
                .stream()
                .filter(r -> r.getName().equals(roomName))
                .toList();
        if(!roomsFilteredByName.isEmpty()) {
            throw new IllegalArgumentException("A room with this name already exists at given campus.");
        }
    }
}



