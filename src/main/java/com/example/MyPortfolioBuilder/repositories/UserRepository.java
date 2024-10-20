package com.example.MyPortfolioBuilder.repositories;

import com.example.MyPortfolioBuilder.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

