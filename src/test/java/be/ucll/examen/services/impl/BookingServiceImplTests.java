package be.ucll.examen.services.impl;

import be.ucll.examen.TestDataUtil;
import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.domain.entities.Room;
import be.ucll.examen.domain.entities.User;
import be.ucll.examen.repositories.BookingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookingServiceImplTests {
    @InjectMocks
    private BookingServiceImpl underTest;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private RoomServiceImpl roomService;

    @Test
    public void createMethod_savesBooking_userExists() {
        User user = TestDataUtil.createTestUser();
        Booking bookingToSave = TestDataUtil.createTestBooking(user);
        Booking savedBooking = TestDataUtil.createTestBooking(user);

        when(userService.findById(user.getId())).thenReturn(user);
        when(bookingRepository.save(bookingToSave)).thenReturn(savedBooking);

        Booking response = underTest.create(user.getId(), bookingToSave);

        assertEquals(response.getId(), savedBooking.getId());
        assertEquals(response.getTimeFrom(), savedBooking.getTimeFrom());
        assertEquals(response.getTimeTo(), savedBooking.getTimeTo());
        assertEquals(response.getUserComment(), savedBooking.getUserComment());
        assertEquals(response.getBookingCapacity(), savedBooking.getBookingCapacity());
        assertNotNull(response.getUser());
        assertEquals(response.getBookedRooms(), savedBooking.getBookedRooms());

        verify(userService, times(1)).findById(user.getId());
        verify(bookingRepository, times(1)).save(bookingToSave);
    }

    @Test
    public void createMethod_throwsNoSuchElementException_userDoesNotExist() {
        User user = TestDataUtil.createTestUser();
        Booking bookingToSave = TestDataUtil.createTestBooking(user);

        when(userService.findById(99L)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> underTest.create(99L, bookingToSave));

        verify(userService, times(1)).findById(99L);
        verify(bookingRepository, times(0)).save(bookingToSave);
    }

    @Test
    public void createMethod_throwsIllegalArgumentException_timeFromIsLaterThanTimeTo() {
        User user = TestDataUtil.createTestUser();
        Booking bookingToSave = TestDataUtil.createTestBooking(user);
        bookingToSave.setTimeFrom(LocalDateTime.of(2024, Month.OCTOBER, 20, 16, 00, 00, 000000));
        bookingToSave.setTimeTo(LocalDateTime.of(2024, Month.OCTOBER, 20, 12, 00, 00, 000000));

        when(userService.findById(user.getId())).thenReturn(user);

        assertThrows(IllegalArgumentException.class, () -> underTest.create(user.getId(), bookingToSave));

        verify(userService, times(1)).findById(user.getId());
        verify(bookingRepository, times(0)).save(bookingToSave);
    }

    @Test
    public void createMethod_throwsIllegalArgumentException_bookingIsInThePast() {
        User user = TestDataUtil.createTestUser();
        Booking bookingToSave = TestDataUtil.createTestBooking(user);
        bookingToSave.setTimeFrom(LocalDateTime.of(1987, Month.OCTOBER, 20, 12, 00, 00, 000000));
        bookingToSave.setTimeTo(LocalDateTime.of(1987, Month.OCTOBER, 20, 13, 00, 00, 000000));

        when(userService.findById(user.getId())).thenReturn(user);

        assertThrows(IllegalArgumentException.class, () -> underTest.create(user.getId(), bookingToSave));

        verify(userService, times(1)).findById(user.getId());
        verify(bookingRepository, times(0)).save(bookingToSave);
    }

    @Test
    public void addRoomToBookingMethod_addsRoomToBooking_validRoomBookingUser() {
        Room roomToAdd = TestDataUtil.createTestRoom(TestDataUtil.createTestCampusA());
        User user = TestDataUtil.createTestUser();
        Booking bookingToUpdate = TestDataUtil.createTestBooking(user);

        List<Booking> bookings = new ArrayList<>();
        bookings.add(bookingToUpdate);

        Booking expectedResult = TestDataUtil.createTestBooking(user);
        expectedResult.getBookedRooms().add(roomToAdd);

        when(roomService.findById(roomToAdd.getId())).thenReturn(roomToAdd);
        when(userService.findById(user.getId())).thenReturn(user);
        when(bookingRepository.findByUserId(user.getId())).thenReturn(bookings);
        when(bookingRepository.save(bookingToUpdate)).thenReturn(expectedResult);

        Booking response = underTest.addRoomToBooking(user.getId(), bookingToUpdate.getId(), roomToAdd.getId());

        assertEquals(expectedResult.getBookedRooms(), response.getBookedRooms());
        verify(roomService, times(1)).findById(roomToAdd.getId());
        verify(userService, times(1)).findById(user.getId());
        verify(bookingRepository, times(1)).findByUserId(user.getId());
        verify(bookingRepository, times(1)).save(bookingToUpdate);
    }

    @Test
    public void addRoomToBookingMethod_throwsIllegalArgumentException_canOnlyBookRoomOnceInSameBooking() {
        // CONSTRAINT: Een lokaal mag binnen je reservatie maar 1 keer worden gereserveerd.

        Room roomToAdd = TestDataUtil.createTestRoom(TestDataUtil.createTestCampusA());
        User user = TestDataUtil.createTestUser();
        Booking bookingToUpdate = TestDataUtil.createTestBooking(user);
        bookingToUpdate.getBookedRooms().add(roomToAdd); // lokaal is reeds geboekt

        List<Booking> bookings = new ArrayList<>();
        bookings.add(bookingToUpdate);

        when(roomService.findById(roomToAdd.getId())).thenReturn(roomToAdd);
        when(userService.findById(user.getId())).thenReturn(user);
        when(bookingRepository.findByUserId(user.getId())).thenReturn(bookings);
        when(bookingRepository.save(bookingToUpdate)).thenReturn(bookingToUpdate);

        assertThrows(IllegalArgumentException.class, () -> underTest.addRoomToBooking(user.getId(), bookingToUpdate.getId(), roomToAdd.getId()));
        verify(roomService, times(1)).findById(roomToAdd.getId());
        verify(userService, times(1)).findById(user.getId());
        verify(bookingRepository, times(1)).findByUserId(user.getId());
        verify(bookingRepository, times(0)).save(bookingToUpdate);
    }

    @Test
    public void addRoomToBookingMethod_throwsIllegalArgumentException_roomAlreadyBookedInDifferentBooking() {
        // CONSTRAINT: Een lokaal mag niet reeds gereserveerd zijn door een andere reservatie.

        Room roomToAdd = TestDataUtil.createTestRoom(TestDataUtil.createTestCampusA());
        User user = TestDataUtil.createTestUser();
        Booking bookingToUpdate = TestDataUtil.createTestBooking(user);
        Booking anotherBooking = TestDataUtil.createTestBooking(user);
        anotherBooking.setId(2L);
        roomToAdd.getBookedBy().add(anotherBooking); // lokaal is reeds geboekt in een andere boeking

        List<Booking> bookings = new ArrayList<>();
        bookings.add(bookingToUpdate);
        bookings.add(anotherBooking);

        when(roomService.findById(roomToAdd.getId())).thenReturn(roomToAdd);
        when(userService.findById(user.getId())).thenReturn(user);
        when(bookingRepository.findByUserId(user.getId())).thenReturn(bookings);
        when(bookingRepository.save(bookingToUpdate)).thenReturn(bookingToUpdate);

        assertThrows(IllegalArgumentException.class, () -> underTest.addRoomToBooking(user.getId(), bookingToUpdate.getId(), roomToAdd.getId()));
        verify(roomService, times(1)).findById(roomToAdd.getId());
        verify(userService, times(1)).findById(user.getId());
        verify(bookingRepository, times(1)).findByUserId(user.getId());
        verify(bookingRepository, times(0)).save(bookingToUpdate);
    }
}
