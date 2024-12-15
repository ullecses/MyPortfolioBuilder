package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Language;
import com.example.myportfoliobuilder.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findAllByUserId(Long userId);
}
