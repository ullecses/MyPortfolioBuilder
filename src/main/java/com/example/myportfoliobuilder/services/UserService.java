package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.models.User;
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
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Метод для регистрации нового пользователя
    public User registerUser(String email, String rawPassword) {
        LOGGER.info("Registering new user with email: " + email);

        // Проверка на существование пользователя с данным email
        if (userRepository.findByEmail(email) != null) {
            LOGGER.warn("User with email " + email + " already exists.");
            throw new IllegalArgumentException("User with email " + email + " already exists.");
        }

        User user = new User();
        user.setEmail(email);

        // Хэшируем пароль перед сохранением
        String hashedPassword = PasswordUtil.hashPassword(rawPassword);
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDate.now());

        User savedUser = userRepository.save(user);
        LOGGER.info("User registered successfully with id: " + savedUser.getId());
        return savedUser;
    }

    // Получить всех пользователей
    public List<User> findAll() {
        LOGGER.info("Fetching all users");
        return userRepository.findAll();
    }

    // Найти пользователя по ID
    public Optional<User> findById(Long id) {
        LOGGER.info("Fetching user with id: " + id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            LOGGER.info("User found with id: " + id);
        } else {
            LOGGER.warn("User not found with id: " + id);
            throw new IllegalArgumentException("User with id " + id + " not exists.");
        }
        return user;
    }

    // Сохранить нового пользователя
    public User saveUser(User user) {
        LOGGER.info("Saving user with email: " + user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            LOGGER.warn("User with email " + user.getEmail() + " already exists.");
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        }

        User savedUser = userRepository.save(user);
        LOGGER.info("User saved successfully with id: " + savedUser.getId());
        return savedUser;
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
        LOGGER.info("Deleting user with id: " + id);

        if (!userRepository.existsById(id)) {
            LOGGER.warn("User not found with id: " + id);
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        LOGGER.info("User deleted successfully with id: " + id);
    }
}
