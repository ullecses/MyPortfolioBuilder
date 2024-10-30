package com.example.MyPortfolioBuilder.controllers;

import com.example.MyPortfolioBuilder.dto.UserRequestDTO;
import com.example.MyPortfolioBuilder.models.User;
//import com.example.MyPortfolioBuilder.services.JwtService;
import com.example.MyPortfolioBuilder.services.JwtService;
import com.example.MyPortfolioBuilder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.MyPortfolioBuilder.config.WebConfig.IPFRONT;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = IPFRONT)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO userDetails) {
        try {
            String result;
            boolean token = userService.login(userDetails.getEmail(), userDetails.getPassword());
            if (token) {
                result = jwtService.generateToken(userDetails.getEmail());
            } else {
                result = "not ok";
            }
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/id/{id}")
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
