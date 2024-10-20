package com.example.MyPortfolioBuilder.services;

import com.example.MyPortfolioBuilder.models.User;
import com.example.MyPortfolioBuilder.repositories.UserRepository;
import com.example.MyPortfolioBuilder.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Метод для регистрации нового пользователя
    public User registerUser(String email, String rawPassword) {
        User user = new User();
        user.setEmail(email);

        // Хэшируем пароль перед сохранением
        String hashedPassword = PasswordUtil.hashPassword(rawPassword);
        user.setPassword(hashedPassword);

        user.setCreatedAt(LocalDate.now());

        return userRepository.save(user);
    }

    // Метод для проверки логина
    /*public boolean login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Проверка пароля
            return PasswordUtil.checkPassword(rawPassword, user.getPassword());
        }
        return false;
    }*/

    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Найти пользователя по ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Сохранить нового пользователя
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /*// Обновить пользователя
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        return userRepository.save(user);
    }*/

    // Удалить пользователя
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}