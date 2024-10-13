package com.example.MyPortfolioBuilder.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Lob
    private byte[] photo;

    @Column(name = "created_at")
    private LocalDate createdAt;

}
