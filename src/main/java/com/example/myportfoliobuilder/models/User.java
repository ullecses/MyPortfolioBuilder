package com.example.myportfoliobuilder.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "business_trips")
    private Enums.BusinessTrips businessTrips;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private Enums.EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode")
    private Enums.WorkMode workMode;

    @Column(name = "desiredPosition")
    private String desiredPosition;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    // Связь с портфолио
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Portfolio> portfolios;

    // Связь с работами
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Work> works;

    // Связь с образованиями
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Education> educations;

    // Связь с языками
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Language> languages;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Photo photo;
}
