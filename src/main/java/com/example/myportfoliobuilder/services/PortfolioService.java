package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.dto.PortfolioRequestDTO;
import com.example.myportfoliobuilder.models.Portfolio;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private static final String PORTFOLIO_NOT_FOUND = "Portfolio not found with id: ";
    private final UserService userService;


    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, UserService userService) {

        this.portfolioRepository = portfolioRepository;
        this.userService = userService;
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Portfolio getPortfolioById(Long id) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(id);
        return portfolio.orElseThrow(() -> new RuntimeException(PORTFOLIO_NOT_FOUND + id));
    }

    public Portfolio createPortfolio(PortfolioRequestDTO portfolioRequest) {

        User user = userService.findById(portfolioRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + portfolioRequest.getUserId()));

        // Создаем новый объект Portfolio и ассоциируем с пользователем
        Portfolio portfolio = new Portfolio();
        portfolio.setTitle(portfolioRequest.getTitle());
        portfolio.setUser(user);
        portfolio.setDescription(portfolioRequest.getDescription());
        portfolio.setCreatedAt(LocalDateTime.now());
        portfolio.setCreatedAt(LocalDateTime.now());

        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Long id, Portfolio portfolioDetails) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PORTFOLIO_NOT_FOUND + id));

        // Обновляем поля портфолио
        existingPortfolio.setTitle(portfolioDetails.getTitle());
        existingPortfolio.setDescription(portfolioDetails.getDescription());
        existingPortfolio.setUpdatedAt(LocalDateTime.now());

        return portfolioRepository.save(existingPortfolio);
    }

    public void deletePortfolio(Long id) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PORTFOLIO_NOT_FOUND + id));
        portfolioRepository.delete(existingPortfolio);
    }
}
