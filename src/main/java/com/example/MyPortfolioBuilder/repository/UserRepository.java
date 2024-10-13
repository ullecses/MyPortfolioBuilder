package com.example.MyPortfolioBuilder.repository;

import com.example.MyPortfolioBuilder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

