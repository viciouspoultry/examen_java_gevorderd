package be.ucll.examen.repositories;

import be.ucll.examen.domain.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT * FROM users u WHERE u.first_name LIKE '%:nameMatches%' OR u.last_name LIKE '%:nameMatches%'",
            nativeQuery = true)
    List<User> findByNameMatches(String nameMatches);
}
