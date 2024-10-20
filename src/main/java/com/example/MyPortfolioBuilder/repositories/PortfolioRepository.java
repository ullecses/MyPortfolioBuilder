package com.example.MyPortfolioBuilder.repositories;

import com.example.MyPortfolioBuilder.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    // JpaRepository уже содержит все базовые CRUD методы, такие как findAll(), findById(), save(), deleteById(), etc.
    // При необходимости, сюда добавить кастомные методы для поиска или фильтрации

    List<Portfolio> findByUserId(Long userId);
}
