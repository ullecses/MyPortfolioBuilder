package com.example.MyPortfolioBuilder.controllers;

import com.example.MyPortfolioBuilder.dto.PortfolioRequestDTO;
import com.example.MyPortfolioBuilder.services.PortfolioService;
import com.example.MyPortfolioBuilder.models.*;

import com.example.MyPortfolioBuilder.services.UserService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {


    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private UserService userService;


    // Получить список всех портфолио
    @GetMapping
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        return ResponseEntity.ok(portfolios);
    }

    // Получить портфолио по id
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }

    // Создать новое портфолио
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody PortfolioRequestDTO portfolioRequest) {
        // Сохраняем портфолио
        Portfolio savedPortfolio = portfolioService.createPortfolio(portfolioRequest);

        return ResponseEntity.ok(savedPortfolio);
    }

    // Обновить портфолио
    @PutMapping("/{id}")
        public ResponseEntity<Portfolio> updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolioDetails) {
        Portfolio updatedPortfolio = portfolioService.updatePortfolio(id, portfolioDetails);
            return ResponseEntity.ok(updatedPortfolio);
        }

    // Удалить портфолио
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.noContent().build();
    }
}
