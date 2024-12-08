package com.example.myportfoliobuilder.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    public String getUserLanguage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lang".equals(cookie.getName())) {
                    return cookie.getValue();  // Возвращаем значение языка из куки
                }
            }
        }
        return "en";
    }
}

