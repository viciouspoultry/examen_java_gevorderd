package be.ucll.examen.services;

import be.ucll.examen.domain.entities.Booking;

import java.util.List;

public interface BookingService {
    Booking create(Long userId, Booking booking);
    List<Booking> findAll(Long userId);
    Booking findById(Long userId, Long bookingId);
    Booking update(Long userId, Long bookingId, Booking booking);
    void deleteById(Long userId, Long bookingId);
}
