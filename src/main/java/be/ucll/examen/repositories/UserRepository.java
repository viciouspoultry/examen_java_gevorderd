package be.ucll.examen.repositories;

import be.ucll.examen.domain.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByFirstNameContaining(String nameMatches);
    List<User> findByLastNameContaining(String nameMatches);
}
