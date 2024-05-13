package be.ucll.examen.services.Impl;

import be.ucll.examen.domain.entities.UserEntity;
import be.ucll.examen.repositories.UserRepository;
import be.ucll.examen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserEntity create(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with id value of " + userId + "."));
    }

    public List<UserEntity> findByNameContaining(String nameMatches) {
        return userRepository.findByNameContaining(nameMatches);
    }

    @Override
    public UserEntity update(Long userId, UserEntity updatedUser) {
        UserEntity userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with id value of " + userId + "."));
        updatedUser.setId(userToUpdate.getId());
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteById(Long userId) {
        UserEntity userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with id value of " + userId + "."));
        userRepository.delete(userToDelete);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
