package be.ucll.examen.services.impl;

import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.domain.entities.Room;
import be.ucll.examen.domain.entities.User;
import be.ucll.examen.repositories.BookingRepository;
import be.ucll.examen.services.BookingService;
import be.ucll.examen.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserServiceImpl userService;
    private final RoomServiceImpl roomService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserServiceImpl userService,
                              RoomServiceImpl roomService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public Booking create(Long userId, Booking bookingToCreate) {
        User foundUser = userService.findById(userId);

        // CONSTRAINT: Start van de reservatie moet voor het einde van de reservatie liggen
        Validator.startTimeIsBeforeEndTime(bookingToCreate.getTimeFrom(), bookingToCreate.getTimeTo());

        // CONSTRAINT: Je mag niet in het verleden reserveren
        Validator.bookingIsNotInThePast(bookingToCreate);

        bookingToCreate.setUser(foundUser);
        return bookingRepository.save(bookingToCreate);
    }

    @Override
    public List<Booking> findAll(Long userId) {
        User foundUser = userService.findById(userId);
        return bookingRepository.findByUserId(foundUser.getId());
    }

    @Override
    public Booking findById(Long userId, Long bookingId) {
        User foundUser = userService.findById(userId);
        return bookingRepository
                .findByUserId(foundUser.getId())
                .stream()
                .filter(b -> b.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No booking found with id value of " + bookingId + "."));
    }

    @Override
    public Booking update(Long userId, Long bookingId, Booking updatedBooking) {
        // CONSTRAINT: Start van de reservatie moet voor het einde van de reservatie liggen
        Validator.startTimeIsBeforeEndTime(updatedBooking.getTimeFrom(), updatedBooking.getTimeTo());

        // CONSTRAINT: Je mag niet in het verleden reserveren
        Validator.bookingIsNotInThePast(updatedBooking);

        Booking bookingToUpdate = this.findById(userId, bookingId);
        updatedBooking.setId(bookingToUpdate.getId());
        updatedBooking.setUser(userService.findById(userId));
        return bookingRepository.save(updatedBooking);
    }

    public Booking addRoomToBooking(Long userId, Long bookingId, Long roomId) {
        Room roomToAdd = roomService.findById(roomId);
        Booking bookingToUpdate =  this.findById(userId, bookingId);

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
        User userToUpdate = userService.findById(userId);
        Booking bookingToDelete = this.findById(userId, bookingId);
        userToUpdate.getBookings().remove(bookingToDelete);
        userService.update(userId, userToUpdate);
        bookingRepository.delete(bookingToDelete);
    }

    public boolean checkAvailability(Booking bookingToBeUpdated, Room roomToBeAdded) {
        // check for time-span overlap of booking where room will be added to
        // and the time-spans of all other bookings the room is already booked by.
        LocalDateTime TF1 = bookingToBeUpdated.getTimeFrom();
        LocalDateTime TT1 = bookingToBeUpdated.getTimeTo();

        Set<Booking> bookedBy = roomToBeAdded.getBookedBy();

        for(Booking bookingToCheck : bookedBy) {
            LocalDateTime TF2 = bookingToCheck.getTimeFrom();
            LocalDateTime TT2 = bookingToCheck.getTimeFrom();

            if ( !((TF2.isBefore(TF1) && TT2.isBefore(TF1)) ||
                    (TF2.isAfter(TT1) && TT2.isAfter(TT1))) ) {
                return false;
            }
        }
        return true;
    }
}
