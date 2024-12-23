package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.models.Language;
import com.example.myportfoliobuilder.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public Language saveLanguage(Language language) {
        return languageRepository.save(language);
    }

    public void deleteAllLanguages(Long userId) {
        languageRepository.deleteAllByUserId(userId);
    }

    public List<Language> getLanguagesByUserId(Long userId) {
        return languageRepository.findAllByUserId(userId);
    }
}
