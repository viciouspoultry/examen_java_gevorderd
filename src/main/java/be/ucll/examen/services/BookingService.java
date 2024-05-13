package be.ucll.examen.services;

import be.ucll.examen.domain.entities.BookingEntity;

import java.util.List;

public interface BookingService {
    BookingEntity create(Long userId, BookingEntity booking);
    List<BookingEntity> findAll(Long userId);
    BookingEntity findById(Long userId, Long bookingId);
    BookingEntity update(Long userId, Long bookingId, BookingEntity booking);
    void deleteById(Long userId, Long bookingId);
}
