package be.ucll.examen;

import be.ucll.examen.domain.dto.CampusDto;
import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.domain.entities.Campus;
import be.ucll.examen.domain.entities.Room;
import be.ucll.examen.domain.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Campus createTestCampusA() {
        Campus campus = new Campus();
        campus.setName("TestNameA");
        campus.setAddress("TestAddressA");
        campus.setParkingCapacity(200);
        campus.setRooms(new ArrayList<>());
        campus.setNumberOfRooms(0);
        return campus;
    }

    public static CampusDto createTestCampusDtoA() {
        CampusDto dto = new CampusDto();
        dto.setName("TestNameA");
        dto.setAddress("TestAddressA");
        dto.setParkingCapacity(200);
        dto.setRooms(new ArrayList<>());
        dto.setNumberOfRooms(0);
        return dto;
    }

    public static CampusDto createTestCampusDtoB() {
        CampusDto dto = new CampusDto();
        dto.setName("TestNameB");
        dto.setAddress("TestAddressB");
        dto.setParkingCapacity(200);
        dto.setRooms(new ArrayList<>());
        dto.setNumberOfRooms(0);
        return dto;
    }

    public static Room createTestRoom(Campus campus) {
        Room room = new Room();
        room.setId(1L);
        room.setName("TestNameA");
        room.setType("TestTypeA");
        room.setCapacity(50);
        room.setFloorNumber(2);
        room.setCampus(campus);
        return room;
    }

    public static User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Stan");
        user.setLastName("Outtier");
        user.setDateOfBirth(LocalDate.of(1987, Month.OCTOBER, 20));
        user.setBookings(new ArrayList<>());
        return user;
    }

    public static Booking createTestBooking(User user) {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setTimeFrom(LocalDateTime.of(2024, Month.OCTOBER, 20, 12, 00, 00, 000000));
        booking.setTimeTo(LocalDateTime.of(2024, Month.OCTOBER, 20, 13, 00, 00, 000000));
        booking.setUserComment("TestComment");
        booking.setBookingCapacity(0);
        booking.setUser(createTestUser());
        booking.setBookedRooms(new HashSet<>());
        return booking;
    }
}
