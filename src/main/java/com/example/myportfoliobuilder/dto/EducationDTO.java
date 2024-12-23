package com.example.myportfoliobuilder.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class EducationDTO {
    private String specialization;
    private String institution;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private String educationInfo;
}

