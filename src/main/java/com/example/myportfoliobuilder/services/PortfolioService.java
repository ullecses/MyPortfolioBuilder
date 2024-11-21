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
    @Autowired
    private UserService userService;


    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {

        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Portfolio getPortfolioById(Long id) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(id);
        return portfolio.orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));
    }

    public Portfolio createPortfolio(PortfolioRequestDTO portfolioRequest) {

        User user = userService.findById(portfolioRequest.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + portfolioRequest.getUser_id()));

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
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));

        // Обновляем поля портфолио
        existingPortfolio.setTitle(portfolioDetails.getTitle());
        existingPortfolio.setDescription(portfolioDetails.getDescription());
        existingPortfolio.setUpdatedAt(LocalDateTime.now());

        return portfolioRepository.save(existingPortfolio);
    }

    public void deletePortfolio(Long id) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));
        portfolioRepository.delete(existingPortfolio);
    }
}
