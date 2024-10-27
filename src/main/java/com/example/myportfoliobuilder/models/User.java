package com.example.myportfoliobuilder.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final Logger LOGGER = Logger.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "password_hash")
    private String password;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private Enums.Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    // Связь с портфолио
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Portfolio> portfolios;

    // Связь с профессиональной информацией
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Professional> professionals;

    public void setName(String name) {
        LOGGER.info("Setting name: " + name);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSurname(String surname) {
        LOGGER.info("Setting surname: " + surname);
        this.surname = surname;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setEmail(String email) {
        LOGGER.info("Setting email: " + email);
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String hashedPassword) {
        LOGGER.info("Setting password hash.");
        this.password = hashedPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setCreatedAt(LocalDate now) {
        LOGGER.info("Setting creation date: " + now);
        this.createdAt = now;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
