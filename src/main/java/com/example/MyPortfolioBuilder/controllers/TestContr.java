package com.example.MyPortfolioBuilder.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.MyPortfolioBuilder.config.WebConfig.IPFRONT;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = IPFRONT)
public class TestContr {
    // Получить список всех портфолио
    @GetMapping
    public String getIndex() {
        return "index";
    }
}
