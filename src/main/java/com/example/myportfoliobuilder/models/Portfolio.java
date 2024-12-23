package com.example.myportfoliobuilder.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "portfolios")
public class Portfolio {

    private static final Logger LOGGER = Logger.getLogger(Portfolio.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкрементируемый ID
    private Long id;

    @Column(name = "title", nullable = false) // Столбец title не может быть пустым
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*@OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Picture> pictures;*/

    // Конструкторы
    public Portfolio() {
        LOGGER.info("Portfolio object created with default constructor.");
    }

    public Portfolio(String title, String description) {
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        LOGGER.info("Portfolio object created with title: " + title + ", description: " + description);
    }
}
