package be.ucll.examen.repositories;

import be.ucll.examen.domain.entities.BookingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Long> {
    List<BookingEntity> findByUserId(Long userId);
}
