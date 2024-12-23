package com.example.myportfoliobuilder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String IPFRONT = "http://localhost:3000";  // IP адрес фронтенда

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(IPFRONT)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
