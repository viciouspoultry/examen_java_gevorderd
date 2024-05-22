package be.ucll.examen.validators;

import be.ucll.examen.domain.entities.Booking;
import be.ucll.examen.domain.entities.Campus;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Validator {
    public static void startTimeIsBeforeEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        if(startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new IllegalArgumentException("End time must be AFTER start time.");
            }
        }
    }

    public static void bookingIsNotInThePast(Booking booking) {
        if(booking.getTimeFrom().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time of booking must be in the future.");
        }
    }

    public static void roomNameDoesNotExist(Campus campus, String roomName) {
        if(campus.getRooms().stream().anyMatch(room -> room.getName().equals(roomName))) {
            throw new IllegalArgumentException("A room with this name already exists at given campus.");
        }
    }
}
