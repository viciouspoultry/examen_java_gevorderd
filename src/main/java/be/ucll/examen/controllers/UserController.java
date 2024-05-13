package be.ucll.examen.controllers;

import be.ucll.examen.domain.dto.UserDto;
import be.ucll.examen.domain.entities.UserEntity;
import be.ucll.examen.mappers.Mapper;
import be.ucll.examen.services.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserServiceImpl userService;
    private final Mapper<UserEntity, UserDto> userMapper;


    @Autowired
    public UserController(UserServiceImpl userService,
                          Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        UserEntity userToCreate = userMapper.mapFrom(dto);
        UserDto response = userMapper.mapTo(userService.create(userToCreate));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam(value = "nameMatches", required = false) String nameMatches) {
        List<UserDto> response = userService.findByNameContaining(nameMatches)
                .stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{user-id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("user-id") Long userId) {
        UserDto response = userMapper.mapTo(userService.findById(userId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{user-id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("user-id") Long userId,
                                              @RequestBody UserDto dto) {
        UserEntity updatedUser = userMapper.mapFrom(dto);
        UserDto response = userMapper.mapTo(userService.update(userId, updatedUser));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{user-id}")
    public ResponseEntity deleteUserById(@PathVariable("user-id") Long userId) {
        userService.deleteById(userId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/users")
    public ResponseEntity deleteAllUsers() {
        userService.deleteAll();
        return ResponseEntity
                .noContent()
                .build();
    }
}
