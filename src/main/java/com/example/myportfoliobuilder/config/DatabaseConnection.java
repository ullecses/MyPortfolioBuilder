package com.example.myportfoliobuilder.config;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class);
    private static final String URL = "jdbc:postgresql://localhost:5432/MyPortfolioBuilder"; // URL для подключения
    private static final String USER = "postgres"; // Имя пользователя PostgreSQL
    private static final String PASSWORD = "7340"; // Пароль PostgreSQL

    private DatabaseConnection() {
        throw new IllegalStateException("Utility class");
    }

    public static void connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("Connected to PostgreSQL database!");
        } catch (SQLException e) {
            LOGGER.error("Connection to PostgreSQL failed: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    LOGGER.info("Database connection closed.");
                } catch (SQLException e) {
                    LOGGER.warn("Failed to close the database connection: " + e.getMessage(), e);
                }
            }
        }
    }
}
