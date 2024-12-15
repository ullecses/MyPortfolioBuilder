package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Education;
import com.example.myportfoliobuilder.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {}

