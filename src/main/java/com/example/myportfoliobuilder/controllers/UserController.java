package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.dto.UserRequestDTO;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private static final String IN_CONTROLLER = " in UserController";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получить всех пользователей
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        LOGGER.info("Received request to fetch all users" + IN_CONTROLLER);
        List<User> users = userService.findAll();
        LOGGER.info("Returning " + users.size() + " users");
        return ResponseEntity.ok(users);
    }

    // Обработка POST-запроса для регистрации нового пользователя
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDTO userDetails) {
        LOGGER.info("Received request to register a new user with email: " + userDetails.getEmail() + IN_CONTROLLER);
        User newUser = userService.registerUser(userDetails.getEmail(), userDetails.getPassword());
        LOGGER.info("User registered successfully with id: " + newUser.getId());
        return ResponseEntity.ok(newUser);
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        LOGGER.info("Received request to fetch user with id: " + id + IN_CONTROLLER);
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            LOGGER.info("User found with id: " + id);
            return ResponseEntity.ok(user);
        } else {
            LOGGER.warn("User not found with id: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    // Создать нового пользователя
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOGGER.info("Received request to create a new user with email: " + user.getEmail() + IN_CONTROLLER);
        User createdUser = userService.saveUser(user);
        LOGGER.info("User created successfully with id: " + createdUser.getId());
        return ResponseEntity.ok(createdUser);
    }

    // Обновить пользователя
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        LOGGER.info("Received request to update user with id: " + id + IN_CONTROLLER);
        User updatedUser = userService.updateUser(id, userDetails);
        LOGGER.info("User updated successfully with id: " + id);
        return ResponseEntity.ok(updatedUser);
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        LOGGER.info("Received request to delete user with id: " + id + IN_CONTROLLER);
        userService.deleteUser(id);
        LOGGER.info("User deleted successfully with id: " + id);
        return ResponseEntity.noContent().build();
    }
}
