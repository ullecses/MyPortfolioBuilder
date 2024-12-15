package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.models.Education;
import com.example.myportfoliobuilder.repositories.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {
    @Autowired
    private EducationRepository educationRepository;

    public Education saveEducation(Education education) {
        return educationRepository.save(education);
    }

    public List<Education> getEducationsByUserId(Long userId) {
        return educationRepository.findAllByUserId(userId);
    }
}
