package be.ucll.examen.services;

import be.ucll.examen.domain.entities.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity create(UserEntity user);
    List<UserEntity> findAll();
    UserEntity findById(Long userId);
    UserEntity update(Long userId, UserEntity user);
    void deleteById(Long userId);
    void deleteAll();
}
