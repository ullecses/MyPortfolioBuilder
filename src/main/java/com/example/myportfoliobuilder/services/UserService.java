package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.models.EmailVerificationToken;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.repositories.EmailVerificationTokenRepository;
import com.example.myportfoliobuilder.repositories.UserRepository;
import com.example.myportfoliobuilder.util.PasswordUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;


    // Метод для регистрации нового пользователя
    public User registerUser(String email, String rawPassword) {
        LOGGER.info("Attempting to register new user with email: " + email);
        Optional<EmailVerificationToken> emailVerificationTokenOptional = emailVerificationTokenRepository.findByEmail(email);

        if (emailVerificationTokenOptional.isPresent() && !emailVerificationTokenOptional.get().isVerified()) {
            LOGGER.error("Email is not verified for: " + email);
            throw new RuntimeException("Email is not verified.");
        }

        User user = new User();
        user.setEmail(email);

        // Хэшируем пароль перед сохранением
        String hashedPassword = PasswordUtil.hashPassword(rawPassword);
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDate.now());

        User savedUser = userRepository.save(user);
        LOGGER.info("User registered successfully with email: " + email + " and id: " + savedUser.getId());
        return savedUser;
    }

    // Метод для проверки логина
    public boolean login(String email, String rawPassword) {
        LOGGER.info("Attempting login for email: " + email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Получаем пользователя
            // Проверка пароля
            boolean isPasswordCorrect = PasswordUtil.checkPassword(rawPassword, user.getPassword());
            if (isPasswordCorrect) {
                LOGGER.info("Login successful for email: " + email);
            } else {
                LOGGER.warn("Incorrect password for email: " + email);
            }
            return isPasswordCorrect;
        } else {
            LOGGER.warn("User not found with email: " + email);
            return false;
        }
    }

    // Найти всех пользователей
    public List<User> findAll() {
        LOGGER.info("Fetching all users.");
        List<User> users = userRepository.findAll();
        LOGGER.info("Found " + users.size() + " users.");
        return users;
    }

    // Найти пользователя по ID
    public Optional<User> findById(Long id) {
        LOGGER.info("Fetching user with id: " + id);
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        LOGGER.info("Fetching user with email: " + email);
        return userRepository.findByEmail(email);
    }

    // Сохранить нового пользователя
    public User saveUser(User user) {
        LOGGER.info("Saving new user with email: " + user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            LOGGER.warn("User with email " + user.getEmail() + " already exists.");
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        }

        User savedUser = userRepository.save(user);
        LOGGER.info("User saved successfully with id: " + savedUser.getId());
        return savedUser;
    }

    public void updateUser(User user) {
        LOGGER.info("Saving user with email: " + user.getEmail());
        User savedUser = userRepository.save(user);
        LOGGER.info("User saved successfully with id: " + savedUser.getId());
    }

    // Обновить пользователя
    public User updateUser(Long id, User userDetails) {
        LOGGER.info("Updating user with id: " + id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            LOGGER.warn("User not found with id: " + id);
            return new IllegalArgumentException("User not found with id: " + id);
        });

        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setEmail(userDetails.getEmail());
        user.setPassword(PasswordUtil.hashPassword(userDetails.getPassword()));
        User updatedUser = userRepository.save(user);
        LOGGER.info("User updated successfully with id: " + updatedUser.getId());
        return updatedUser;
    }

    // Удалить пользователя
    public void deleteUser(Long id) {
        LOGGER.info("Attempting to delete user with id: " + id);

        if (!userRepository.existsById(id)) {
            LOGGER.warn("User not found with id: " + id);
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        LOGGER.info("User deleted successfully with id: " + id);
    }
}