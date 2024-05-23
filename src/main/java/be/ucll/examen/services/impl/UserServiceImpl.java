package be.ucll.examen.services.impl;

import be.ucll.examen.domain.entities.User;
import be.ucll.examen.repositories.UserRepository;
import be.ucll.examen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with id value of " + userId + "."));
    }

    @Override
    public List<User> findByNameContaining(String nameMatches) {
        if(nameMatches == null) {
            return StreamSupport.stream(userRepository
                                    .findAll()
                                    .spliterator(),
                            false)
                    .collect(Collectors.toList());
        }
        else {
            return userRepository.findByNameMatches(nameMatches);
        }
    }

    @Override
    public User update(Long userId, User updatedUser) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with id value of " + userId + "."));
        updatedUser.setId(userToUpdate.getId());
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteById(Long userId) {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with id value of " + userId + "."));
        userRepository.delete(userToDelete);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
