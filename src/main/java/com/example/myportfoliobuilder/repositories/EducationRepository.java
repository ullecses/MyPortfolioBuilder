package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findAllByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Education p WHERE p.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}

