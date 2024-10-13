package com.example.MyPortfolioBuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/users/register", "/actuator/health", "/test-db").permitAll() // Разрешить доступ без аутентификации
                        .anyRequest().authenticated() // Остальные запросы требуют аутентификации
                )
                .csrf((csrf) -> csrf.disable()) // Отключить CSRF через лямбда-выражение
                .httpBasic((basic) -> basic.disable()); // Отключить базовую аутентификацию через лямбда-выражение

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
