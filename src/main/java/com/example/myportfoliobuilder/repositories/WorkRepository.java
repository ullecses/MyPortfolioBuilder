package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Work p WHERE p.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
