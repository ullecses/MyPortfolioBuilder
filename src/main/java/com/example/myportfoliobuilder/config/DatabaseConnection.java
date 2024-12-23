package com.example.myportfoliobuilder.config;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class);
    @Value("${spring.datasource.url}")  // Подставляем значение из application.properties
    private String url;

    @Value("${spring.datasource.username}") // Подставляем значение из application.properties
    private String user;

    @Value("${spring.datasource.password}")  // Подставляем значение из application.properties
    private String password;

    private static String staticUrl;
    private static String staticUser;
    private static String staticPassword;

    @Value("${spring.datasource.url}")
    public void setStaticUrl(String url) {
        DatabaseConnection.staticUrl = url;
    }

    @Value("${spring.datasource.username}")
    public void setStaticUser(String user) {
        DatabaseConnection.staticUser = user;
    }

    @Value("${spring.datasource.password}")
    public void setStaticPassword(String password) {
        DatabaseConnection.staticPassword = password;
    }

    public void connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            LOGGER.info("Connected to PostgreSQL database!");
        } catch (SQLException e) {
            LOGGER.error("Connection to PostgreSQL failed: " + e.getMessage(), e);
        }
    }
}
