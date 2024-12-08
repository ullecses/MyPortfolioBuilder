package com.example.myportfoliobuilder.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class LanguageController {

    @PostMapping("/set-language")
    public ResponseEntity<Void> setLanguage(@RequestParam("lang") String lang, HttpServletResponse response) {
        // Создаем куку с языком
        Cookie langCookie = new Cookie("lang", lang);
        langCookie.setMaxAge((int) Duration.ofHours(24).getSeconds());  // Время жизни куки (1 день)
        langCookie.setPath("/");  // Путь для куки (для всех путей)
        response.addCookie(langCookie);  // Добавляем куку в ответ

        return ResponseEntity.ok().build();
    }
}
