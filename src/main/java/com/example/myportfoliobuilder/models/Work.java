package com.example.myportfoliobuilder.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String position; // Должность

    @Column(nullable = false)
    private String company; // Компания

    @Column
    private String city; // Город

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // Дата начала

    @Column(name = "end_date")
    private LocalDate endDate; // Дата окончания

    @Column(name = "jobs_info", columnDefinition = "TEXT")
    private String jobsInfo; // Информация о работе

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Связь с пользователем
}
