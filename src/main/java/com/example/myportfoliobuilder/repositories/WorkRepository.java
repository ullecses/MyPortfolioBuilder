package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByUserId(Long userId);
}
