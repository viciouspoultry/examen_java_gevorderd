package be.ucll.examen.services;

import be.ucll.examen.domain.entities.User;

import java.util.List;

public interface UserService {
    User create(User user);
    List<User> findByNameContaining(String nameMatches);
    User findById(Long userId);
    User update(Long userId, User user);
    void deleteById(Long userId);
    void deleteAll();
}
