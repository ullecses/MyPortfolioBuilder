package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.dto.UserRequestDTO;
import com.example.myportfoliobuilder.models.User;
//import com.example.myportfoliobuilder.services.JwtService;
import com.example.myportfoliobuilder.services.JwtService;
import com.example.myportfoliobuilder.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.myportfoliobuilder.config.WebConfig.IPFRONT;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = IPFRONT)
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private static final String IN_CONTROLLER = " in UserController";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

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
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userDetails) {
        LOGGER.info("Received request to register a new user with email: " + userDetails.getEmail() + IN_CONTROLLER);
        User newUser = userService.registerUser(userDetails.getEmail(), userDetails.getPassword());
        LOGGER.info("User registered successfully with id: " + newUser.getId());

        String token = jwtService.generateToken(userDetails.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("user", newUser);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO userDetails) {
        try {
            boolean isAuthenticated = userService.login(userDetails.getEmail(), userDetails.getPassword());

            String token;

            if (isAuthenticated) {
                token = jwtService.generateToken(userDetails.getEmail());
                LOGGER.info("Token: " + token);
            } else {
                token = "not ok";
            }
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

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
    public User createUser(@RequestBody User user) {
        LOGGER.info("Received request to create a new user with email: " + user.getEmail() + IN_CONTROLLER);
        User createdUser = userService.saveUser(user);
        LOGGER.info("User created successfully with id: " + createdUser.getId());
        return ResponseEntity.ok(createdUser).getBody();
    }

    // Обновить пользователя
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        LOGGER.info("Received request to update user with id: " + id + IN_CONTROLLER);
        User updatedUser = userService.updateUser(id, userDetails);
        LOGGER.info("User updated successfully with id: " + id);
        return ResponseEntity.ok(updatedUser).getBody();
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