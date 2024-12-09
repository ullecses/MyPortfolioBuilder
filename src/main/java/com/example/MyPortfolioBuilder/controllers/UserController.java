package com.example.MyPortfolioBuilder.controllers;

import com.example.MyPortfolioBuilder.dto.UserRequestDTO;
import com.example.MyPortfolioBuilder.models.EmailVerificationToken;
import com.example.MyPortfolioBuilder.models.User;
import com.example.MyPortfolioBuilder.repositories.EmailVerificationTokenRepository;
import com.example.MyPortfolioBuilder.services.EmailService;
import com.example.MyPortfolioBuilder.services.JwtService;
import com.example.MyPortfolioBuilder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.MyPortfolioBuilder.config.WebConfig.IPFRONT;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = IPFRONT)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;


    // Получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userDetails) {
        User newUser = userService.registerUser(userDetails.getEmail(), userDetails.getPassword());

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
                System.out.println("Token: " + token);
            } else {
                token = "not ok";
            }
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // Отправить токен подтверждения email
    @PostMapping("/send-verification-token")
    public ResponseEntity<String> sendVerificationToken(@RequestParam("email") String email) {
        Optional<EmailVerificationToken> tokenOptional = emailVerificationTokenRepository.findByEmail(email);

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setEmail(email);
        emailVerificationToken.setVerified(false);
        emailVerificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(20));
        emailVerificationToken.setToken(emailService.createVerificationToken());
        emailService.sendVerificationEmail(emailVerificationToken.getEmail(), emailVerificationToken.getToken());
        emailVerificationTokenRepository.save(emailVerificationToken);

        return ResponseEntity.ok("Verification email sent.");
    }

    // Проверка токена при подтверждении email
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        Optional<EmailVerificationToken> tokenOptional = emailVerificationTokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        EmailVerificationToken emailToken = tokenOptional.get();

        if (emailToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired. Please request a new verification link.");
        }

        emailToken.setVerified(true);

        emailVerificationTokenRepository.save(emailToken);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "http://localhost:3000/register?email=" + emailToken.getEmail())
                .build();
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
