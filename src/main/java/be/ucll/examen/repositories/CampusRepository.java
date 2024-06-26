package be.ucll.examen.repositories;

import be.ucll.examen.domain.entities.Campus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepository extends CrudRepository<Campus, String> {
}
