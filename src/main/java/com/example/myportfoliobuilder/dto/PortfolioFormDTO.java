package com.example.myportfoliobuilder.dto;

import com.example.myportfoliobuilder.models.Enums;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
public class PortfolioFormDTO {
    private String userEmail;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String country;
    private String citizenship;
    private String phone;
    //private MultipartFile photo; // Для обработки фото
    private Enums.Gender gender;
    private Enums.BusinessTrips businessTrips;
    private Enums.EmploymentType employment;
    private Enums.WorkMode workMode;

    private List<WorkDTO> works; // Список работ
    private List<EducationDTO> educations; // Список образований
    //private List<String> languages; // Список языков
}


