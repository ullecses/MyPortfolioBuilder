package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.dto.PortfolioRequestDTO;
import com.example.myportfoliobuilder.services.PortfolioService;
import com.example.myportfoliobuilder.models.*;

import com.example.myportfoliobuilder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.myportfoliobuilder.config.WebConfig.IPFRONT;


@RestController
@RequestMapping("/api/portfolios")
@CrossOrigin(origins = IPFRONT)
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
