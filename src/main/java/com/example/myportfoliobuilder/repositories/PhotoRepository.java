package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByUserId(Long userId);
    //void deleteByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Photo p WHERE p.user.id = :userId")
    void deleteByUserId(Long userId);
}
