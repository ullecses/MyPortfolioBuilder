package com.example.MyPortfolioBuilder.util;

import com.example.MyPortfolioBuilder.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Метод для хэширования пароля
    public static String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Метод для проверки пароля
    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}