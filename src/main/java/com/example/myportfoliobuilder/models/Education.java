package com.example.myportfoliobuilder.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "educations")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String institution;

    /*@Column
    private String city; // Город*/

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // Дата начала

    @Column(name = "end_date")
    private LocalDate endDate; // Дата окончания

    /*@Column(name = "education_info", columnDefinition = "TEXT")
    private String educationInfo;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user; // Связь с пользователем
}
