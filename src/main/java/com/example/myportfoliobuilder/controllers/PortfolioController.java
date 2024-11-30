package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.dto.PortfolioRequestDTO;
import com.example.myportfoliobuilder.models.Portfolio;
import com.example.myportfoliobuilder.services.PortfolioService;
import com.example.myportfoliobuilder.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.myportfoliobuilder.config.WebConfig.IPFRONT;

@RestController
@RequestMapping("/api/portfolios")
@CrossOrigin(origins = IPFRONT)
public class PortfolioController {

    private static final Logger LOGGER = Logger.getLogger(PortfolioController.class);
    private static final String IN_CONTROLLER = " in PortfolioController";
    private final PortfolioService portfolioService;
    private final UserService userService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, UserService userService) {
        this.portfolioService = portfolioService;
        this.userService = userService;
    }

    // Получить список всех портфолио
    @GetMapping
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        LOGGER.info("Received request to get all portfolios " + IN_CONTROLLER);
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        LOGGER.info("Returning " + portfolios.size() + " portfolios" + IN_CONTROLLER);
        return ResponseEntity.ok(portfolios);
    }

    // Получить портфолио по id
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        LOGGER.info("Received request to get portfolio with id: " + id + IN_CONTROLLER);
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        LOGGER.info("Returning portfolio with id: " + id + IN_CONTROLLER);
        return ResponseEntity.ok(portfolio);
    }

    // Создать новое портфолио
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody PortfolioRequestDTO portfolioRequest) {
        LOGGER.info("Received request to create a new portfolio for user id: " + portfolioRequest.getUserId() + IN_CONTROLLER);
        Portfolio savedPortfolio = portfolioService.createPortfolio(portfolioRequest);
        LOGGER.info("Portfolio created with id: " + savedPortfolio.getId() + IN_CONTROLLER);
        return ResponseEntity.ok(savedPortfolio);
    }

    // Обновить портфолио
    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolioDetails) {
        LOGGER.info("Received request to update portfolio with id: " + id + IN_CONTROLLER);
        Portfolio updatedPortfolio = portfolioService.updatePortfolio(id, portfolioDetails);
        LOGGER.info("Portfolio updated with id: " + id + IN_CONTROLLER);
        return ResponseEntity.ok(updatedPortfolio);
    }

    // Удалить портфолио
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        LOGGER.info("Received request to delete portfolio with id: " + id + IN_CONTROLLER);
        portfolioService.deletePortfolio(id);
        LOGGER.info("Portfolio deleted with id: " + id + IN_CONTROLLER);
        return ResponseEntity.noContent().build();
    }
}
