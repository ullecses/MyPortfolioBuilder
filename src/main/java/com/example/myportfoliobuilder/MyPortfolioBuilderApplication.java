package com.example.myportfoliobuilder;

import com.example.myportfoliobuilder.config.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MyPortfolioBuilderApplication {

	public static void main(String[] args) {
		// Инициализация Spring-контекста
		ApplicationContext context = SpringApplication.run(MyPortfolioBuilderApplication.class, args);

		// Получение бина из контекста
		DatabaseConnection dbConnection = context.getBean(DatabaseConnection.class);
		dbConnection.connect(); // Вызов метода connect()
	}
}
