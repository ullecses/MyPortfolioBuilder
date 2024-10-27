package com.example.MyPortfolioBuilder.controllers;

import com.example.MyPortfolioBuilder.dto.UserRequestDTO;
import com.example.MyPortfolioBuilder.models.User;
import com.example.MyPortfolioBuilder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // Обработка POST-запроса для регистрации нового пользователя
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDTO userDetails) {
        User newUser = userService.registerUser(userDetails.getEmail(), userDetails.getPassword());
        return ResponseEntity.ok(newUser);
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Создать нового пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
