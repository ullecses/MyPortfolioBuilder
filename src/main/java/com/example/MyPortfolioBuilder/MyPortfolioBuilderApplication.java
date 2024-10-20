package com.example.MyPortfolioBuilder;

import com.example.MyPortfolioBuilder.config.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyPortfolioBuilderApplication {

	public static void main(String[] args) {
		DatabaseConnection.connect();
		SpringApplication.run(MyPortfolioBuilderApplication.class, args);
	}
}
