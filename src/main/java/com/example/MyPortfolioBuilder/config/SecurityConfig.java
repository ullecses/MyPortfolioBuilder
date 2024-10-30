package com.example.MyPortfolioBuilder.config;

import com.example.MyPortfolioBuilder.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.example.MyPortfolioBuilder.config.WebConfig.IPFRONT;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter; // фильтр аутентификации JWT

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Включаем поддержку CORS
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF, если это необходимо
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Доступ ко всем ресурсам, начинающимся с /public
                        .requestMatchers("/users/**").authenticated() // Доступ к /user/** только для аутентифицированных пользователей
                        .anyRequest().permitAll() // Разрешаем остальные запросы (опционально)
                )
                .formLogin(form -> form
                        .loginPage("/login") // Указываем кастомный URL для страницы логина
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Укажите здесь URL фронтенда
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Разрешаем все заголовки
        configuration.setAllowCredentials(true); // Разрешаем учетные данные

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Настройки CORS для всех маршрутов
        return source;
    }


    // AuthenticationManager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

/*@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter; // фильтр аутентификации JWT

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF, если это необходимо
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Доступ ко всем ресурсам, начинающимся с /public
                        .requestMatchers("/users/**").authenticated() // Доступ к /user/** только для аутентифицированных пользователей
                        .anyRequest().permitAll() // Разрешаем остальные запросы (опционально)
                )
                .formLogin(form -> form
                        .loginPage("/login") // Указываем кастомный URL для страницы логина
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }


    // AuthenticationManager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}*/
