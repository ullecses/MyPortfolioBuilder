package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.dto.PortfolioRequestDTO;
import com.example.myportfoliobuilder.models.Portfolio;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.services.PortfolioService;
import com.example.myportfoliobuilder.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static com.example.myportfoliobuilder.config.WebConfig.IPFRONT;

@RestController
@RequestMapping("/api/create-portfolio")
@CrossOrigin(origins = IPFRONT)
public class PortfolioCreatingController {
    private final UserService userService;

    public PortfolioCreatingController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserPortfolio(@RequestParam String email) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found with email: " + email));
        }

        User user = userOptional.get();
        Random random = new Random();
        Map<String, Object> portfolioData = Map.of(
                "header", HEADERS.get(random.nextInt(HEADERS.size())),
                "name", user.getName(),
                "surname", user.getSurname(),
                "email", user.getEmail(),
                "citizenship", user.getCitizenship(),
                "country", user.getCountry(),
                "phoneNumber", user.getPhoneNumber()
        );

        return ResponseEntity.ok(portfolioData);
    }

    private static final List<String> HEADERS = List.of(
            "Ответственный и целеустремлённый специалист с отличными навыками организации и планирования. Легко адаптируюсь к новым задачам и стремлюсь к постоянному развитию. Отлично работаю в команде и готов к новым вызовам.",
            "Инициативный и трудолюбивый сотрудник, всегда стремящийся к высоким результатам. Обладаю превосходными коммуникативными навыками и умением находить решения в сложных ситуациях. Надежен и нацелен на долгосрочный успех.",
            "Опытный и мотивированный профессионал с отличными аналитическими способностями. Умею эффективно управлять проектами и устанавливать приоритеты. Всегда поддерживаю положительную атмосферу в команде.",
            "Организованный и внимательный к деталям сотрудник, который успешно справляется с многозадачностью. Быстро обучаюсь и всегда ищу способы улучшить процессы. Отличаюсь надёжностью и ответственным подходом к работе.",
            "Активный и целеустремлённый профессионал, известный своей инициативностью. Способен работать в условиях высокой нагрузки и сохранять высокое качество работы. Обладаю сильной трудовой этикой и высокими моральными стандартами.",
            "Амбициозный и быстро обучающийся специалист, всегда готовый к новым вызовам. Применяю системный подход к решению задач и стремлюсь к максимальной эффективности. Ценю командную работу и всегда открыт к сотрудничеству.",
            "Исполнительный и преданный делу сотрудник, обладающий отличными навыками межличностного взаимодействия. Всегда нацелен на достижение высоких результатов и качественное выполнение задач. Известен своей креативностью и надёжностью.",
            "Проактивный и ориентированный на результат специалист с сильными аналитическими навыками. Легко справляюсь со сложными задачами и всегда ищу новые пути для улучшения. Стремлюсь к постоянному совершенствованию своих навыков."
    );
}
