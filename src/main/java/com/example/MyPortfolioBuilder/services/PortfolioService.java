package com.example.MyPortfolioBuilder.services;

import com.example.MyPortfolioBuilder.models.Portfolio;
import com.example.MyPortfolioBuilder.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

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

    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Long id, Portfolio portfolioDetails) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));

        // Обновляем поля портфолио
        existingPortfolio.setTitle(portfolioDetails.getTitle());
        existingPortfolio.setDescription(portfolioDetails.getDescription());

        return portfolioRepository.save(existingPortfolio);
    }

    public void deletePortfolio(Long id) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));
        portfolioRepository.delete(existingPortfolio);
    }
}
