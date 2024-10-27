package com.example.myportfoliobuilder.models;

import jakarta.persistence.*;

@Entity
@Table(name = "professional")
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_trips")
    private Enums.BusinessTrips businessTrips;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private Enums.EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_character")
    private Enums.WorkCharacter workCharacter;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_schedule")
    private Enums.WorkSchedule workSchedule;

    // Геттеры и сеттеры
}
