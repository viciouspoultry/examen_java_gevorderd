package be.ucll.examen.repositories;

import be.ucll.examen.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM users u " +
            "WHERE (:nameMatches IS null OR " +
            ":nameMatches ='' OR " +
            "u.first_name LIKE %:nameMatches% OR " +
            "u.last_name LIKE %:nameMatches%)", nativeQuery = true)
    List<UserEntity> findByNameContaining(@Param("nameMatches") String nameMatches);
}
