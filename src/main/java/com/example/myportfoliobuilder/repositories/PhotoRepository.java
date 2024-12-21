package com.example.myportfoliobuilder.repositories;

import com.example.myportfoliobuilder.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
