package com.example.myportfoliobuilder.models;

import org.apache.log4j.Logger;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        LOGGER.info("Setting portfolio ID to: " + id);
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        LOGGER.info("Updating title to: " + title);
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        LOGGER.info("Updating description to: " + description);
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        LOGGER.info("Setting createdAt timestamp to: " + createdAt);
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        LOGGER.info("Setting updatedAt timestamp to: " + updatedAt);
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        LOGGER.info("Associating user with ID: " + user.getId() + " to portfolio.");
        this.user = user;
    }

    /*public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        LOGGER.info("Updating list of pictures for portfolio with ID: " + this.id);
        this.pictures = pictures;
    }*/

    // Метод для обновления времени
    @PreUpdate
    public void preUpdate() {
        LOGGER.info("Updating timestamp before saving portfolio with ID: " + this.id);
        this.updatedAt = LocalDateTime.now();
    }
}
