package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {}
