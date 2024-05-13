package be.ucll.examen.services.Impl;

import be.ucll.examen.domain.dto.BookingDto;
import be.ucll.examen.domain.dto.RoomDto;
import be.ucll.examen.domain.dto.UserDto;
import be.ucll.examen.domain.entities.BookingEntity;
import be.ucll.examen.domain.entities.RoomEntity;
import be.ucll.examen.domain.entities.UserEntity;
import be.ucll.examen.mappers.Mapper;
import be.ucll.examen.repositories.BookingRepository;
import be.ucll.examen.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserServiceImpl userService;
    private final RoomServiceImpl roomService;
    private final Mapper<BookingEntity, BookingDto> bookingMapper;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final Mapper<RoomEntity, RoomDto> roomMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserServiceImpl userService,
                              RoomServiceImpl roomService,
                              Mapper<BookingEntity, BookingDto> bookingMapper,
                              Mapper<UserEntity, UserDto> userMapper,
                              Mapper<RoomEntity, RoomDto> roomMapper) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.roomService = roomService;
        this.bookingMapper = bookingMapper;
        this.userMapper = userMapper;
        this.roomMapper = roomMapper;
    }

    @Override
    public BookingEntity create(Long userId, BookingEntity bookingToCreate) {
        UserEntity foundUser = userService.findById(userId);

        // CONSTRAINT: Startdatum van de reservatie moet voor de einddatum liggen
        // CONSTRAINT: Je mag niet in het verleden reserveren
        checkTimeConstraints(bookingToCreate);

        bookingToCreate.setUser(foundUser);
        return bookingRepository.save(bookingToCreate);
    }

    @Override
    public List<BookingEntity> findAll(Long userId) {
        UserEntity foundUser = userService.findById(userId);
        return bookingRepository.findByUserId(foundUser.getId());
    }

    @Override
    public BookingEntity findById(Long userId, Long bookingId) {
        UserEntity foundUser = userService.findById(userId);
        return bookingRepository
                .findByUserId(foundUser.getId())
                .stream()
                .filter(b -> b.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No booking found with id value of " + bookingId + "."));
    }

    @Override
    public BookingEntity update(Long userId, Long bookingId, BookingEntity updatedBooking) {
        BookingEntity bookingToUpdate = this.findById(userId, bookingId);
        updatedBooking.setId(bookingToUpdate.getId());
        updatedBooking.setUser(userService.findById(userId));
        return bookingRepository.save(updatedBooking);
    }

    public BookingEntity addRoomToBooking(Long userId, Long bookingId, Long roomId) {
        RoomEntity roomToAdd = roomService.findById(roomId);
        BookingEntity bookingToUpdate = this.findById(userId, bookingId);
        // CONSTRAINT: Een lokaal mag binnen je reservatie maar 1 keer worden gereserveerd.
        // CONSTRAINT: Een lokaal mag niet reeds gereserveerd zijn door een andere reservatie.
        if(!bookingToUpdate.getBookedRooms().contains(roomToAdd) && checkAvailability(bookingToUpdate, roomToAdd)) {
            bookingToUpdate.getBookedRooms().add(roomToAdd);
            return bookingRepository.save(bookingToUpdate);
        }
        else {
            throw new IllegalArgumentException("Room is not available at this time");
        }
    }

    @Override
    public void deleteById(Long userId, Long bookingId) {
        UserEntity userToUpdate = userService.findById(userId);
        BookingEntity bookingToDelete = this.findById(userId, bookingId);
        userToUpdate.getBookings().remove(bookingToDelete);
        userService.update(userId, userToUpdate);
        bookingRepository.delete(bookingToDelete);
    }

    public boolean checkAvailability(BookingEntity bookingToBeUpdated, RoomEntity roomToBeAdded) {
        // check for time-span overlap of booking where room will be added to
        // and the time-spans of all other bookings the room is already booked by.
        LocalDateTime TF1 = bookingToBeUpdated.getTimeFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime TT1 = bookingToBeUpdated.getTimeTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Set<BookingEntity> bookedBy = roomToBeAdded.getBookedBy();

        for(BookingEntity bookingToCheck : bookedBy) {
            LocalDateTime TF2 = bookingToCheck.getTimeFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime TT2 = bookingToCheck.getTimeFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            if ( !((TF2.isBefore(TF1) && TT2.isBefore(TF1)) ||
                    (TF2.isAfter(TT1) && TT2.isAfter(TT1))) ) {
                return false;
            }
        }
        return true;
    }

    public void checkTimeConstraints(BookingEntity booking) {
        LocalDateTime timeFrom = booking.getTimeFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime timeTo = booking.getTimeTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // CONSTRAINT: Startdatum van de reservatie moet voor de einddatum liggen
        if (timeFrom.isAfter(timeTo)) {
            throw new IllegalArgumentException("End time of booking must be AFTER start time.");
        }
        // CONSTRAINT: Je mag niet in het verleden reserveren
        if(timeFrom.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time of booking must be in the future.");
        }
    }
}
