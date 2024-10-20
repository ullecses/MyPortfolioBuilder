package com.example.MyPortfolioBuilder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/MyPortfolioBuilder"; // URL для подключения
    private static final String USER = "postgres"; // Имя пользователя PostgreSQL
    private static final String PASSWORD = "7340"; // Пароль PostgreSQL

    public static void  connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL database!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
