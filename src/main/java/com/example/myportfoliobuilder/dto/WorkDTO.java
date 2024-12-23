package com.example.myportfoliobuilder.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class WorkDTO {
    private String position;
    private String company;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobsInfo;
}