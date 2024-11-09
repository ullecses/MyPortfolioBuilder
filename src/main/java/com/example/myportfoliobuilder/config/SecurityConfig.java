package com.example.myportfoliobuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/api/users/register", "/actuator/health", "/test-db", "/api/portfolios").permitAll() // Разрешить доступ без аутентификации
//                        .anyRequest().authenticated() // Остальные запросы требуют аутентификации
//                )
//                .csrf(AbstractHttpConfigurer::disable) // Отключить CSRF через лямбда-выражение
//                .httpBasic(AbstractHttpConfigurer::disable); // Отключить базовую аутентификацию через лямбда-выражение
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Отключаем CSRF (если это нужно)
        http.csrf(AbstractHttpConfigurer::disable)
                // Разрешаем все запросы без аутентификации
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/chat").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
