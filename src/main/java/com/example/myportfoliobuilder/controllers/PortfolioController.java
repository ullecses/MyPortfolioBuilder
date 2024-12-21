package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.dto.EducationDTO;
import com.example.myportfoliobuilder.dto.LanguageDTO;
import com.example.myportfoliobuilder.dto.PortfolioFormDTO;
import com.example.myportfoliobuilder.dto.WorkDTO;
import com.example.myportfoliobuilder.models.*;
import com.example.myportfoliobuilder.repositories.UserRepository;
import com.example.myportfoliobuilder.repositories.WorkRepository;
import com.example.myportfoliobuilder.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.example.myportfoliobuilder.config.WebConfig.IPFRONT;

@RestController
@RequestMapping("/api/portfolios")
@CrossOrigin(origins = IPFRONT)
public class PortfolioController {

    private static final Logger LOGGER = Logger.getLogger(PortfolioController.class);
    private static final String IN_CONTROLLER = " in PortfolioController";

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkService workService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private PhotoService photoService;

    @PostMapping("/create")
    public ResponseEntity<String> createPortfolio(@ModelAttribute PortfolioFormDTO formDTO) throws IOException {
        User user = userService.findByEmail(formDTO.getUserEmail()).get();
        user.setName(formDTO.getFirstName());
        user.setSurname(formDTO.getLastName());
        user.setDesiredPosition(formDTO.getDesiredPosition());
        user.setCountry(formDTO.getCountry());
        user.setCitizenship(formDTO.getCitizenship());
        user.setPhoneNumber(formDTO.getPhone());
        user.setEmail(formDTO.getUserEmail());
        user.setGender(formDTO.getGender());
        user.setBusinessTrips(formDTO.getBusinessTrips());
        user.setEmploymentType(formDTO.getEmployment());
        user.setWorkMode(formDTO.getWorkMode());
        userService.saveUser(user);
        // Сохраняем фотографию
        if (formDTO.getPhoto() != null && !formDTO.getPhoto().isEmpty()) {
            MultipartFile photoFile = formDTO.getPhoto();
            photoService.savePhoto(photoFile, user);
        }

        /*if (formDTO.getWorks() != null) {
            for (WorkDTO workDTO : formDTO.getWorks()) {
                Work work = new Work();
                work.setPosition(workDTO.getPosition());
                work.setCompany(workDTO.getCompany());
                work.setCity(workDTO.getCity());
                work.setStartDate(workDTO.getStartDate());
                work.setEndDate(workDTO.getEndDate());
                work.setJobsInfo(workDTO.getJobsInfo());
                work.setUser(user);

                workService.saveWork(work);
            }
        }

        if (formDTO.getEducations() != null) {
            for (EducationDTO educationDTO : formDTO.getEducations()) {
                Education education = new Education();
                education.setSpecialization(educationDTO.getSpecialization());
                education.setInstitution(educationDTO.getInstitution());
                education.setCity(educationDTO.getCity());
                education.setStartDate(educationDTO.getStartDate());
                education.setEndDate(educationDTO.getEndDate());
                education.setEducationInfo(educationDTO.getEducationInfo());
                education.setUser(user);

                educationService.saveEducation(education);
            }
        }

        if (formDTO.getLanguages() != null) {
            for (LanguageDTO languageDTO : formDTO.getLanguages()) {
                Language language = new Language();
                language.setLanguage(languageDTO.getLanguage());
                language.setLevel(languageDTO.getLevel());
                language.setUser(user);

                languageService.saveLanguage(language);
            }
        }*/

        return ResponseEntity.ok("Portfolio created successfully");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> generateUserPortfolio(@RequestParam String email) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found with email: " + email));
        }

        User user = userOptional.get();
        List<Work> works = workService.getWorksByUserId(user.getId());
        List<Education> educations = educationService.getEducationsByUserId(user.getId());
        List<Language> languages = languageService.getLanguagesByUserId(user.getId());

        Random random = new Random();
        Map<String, Object> response = new HashMap<>();
        response.put("header", HEADERS.get(random.nextInt(HEADERS.size())));
        response.put("firstName", user.getName());
        response.put("lastName", user.getSurname());
        response.put("email", user.getEmail());
        response.put("phoneNumber", user.getPhoneNumber());
        response.put("country", user.getCountry());
        response.put("citizenship", user.getCitizenship());
        response.put("desiredPosition", user.getDesiredPosition());
        response.put("businessTrips", user.getBusinessTrips());
        response.put("employment", user.getEmploymentType());
        response.put("workMode", user.getWorkMode());
        response.put("works", works);
        response.put("educations", educations);
        response.put("languages", languages);

        return ResponseEntity.ok(response);
    }

    private static final List<String> HEADERS = List.of(
            "Ответственный и целеустремлённый специалист с отличными навыками организации и планирования. Легко адаптируюсь к новым задачам и стремлюсь к постоянному развитию. Отлично работаю в команде и готов к новым вызовам.",
            "Инициативный и трудолюбивый сотрудник, всегда стремящийся к высоким результатам. Обладаю превосходными коммуникативными навыками и умением находить решения в сложных ситуациях. Надежен и нацелен на долгосрочный успех.",
            "Опытный и мотивированный профессионал с отличными аналитическими способностями. Умею эффективно управлять проектами и устанавливать приоритеты. Всегда поддерживаю положительную атмосферу в команде.",
            "Организованный и внимательный к деталям сотрудник, который успешно справляется с многозадачностью. Быстро обучаюсь и всегда ищу способы улучшить процессы. Отличаюсь надёжностью и ответственным подходом к работе.",
            "Активный и целеустремлённый профессионал, известный своей инициативностью. Способен работать в условиях высокой нагрузки и сохранять высокое качество работы. Обладаю сильной трудовой этикой и высокими моральными стандартами.",
            "Амбициозный и быстро обучающийся специалист, всегда готовый к новым вызовам. Применяю системный подход к решению задач и стремлюсь к максимальной эффективности. Ценю командную работу и всегда открыт к сотрудничеству.",
            "Исполнительный и преданный делу сотрудник, обладающий отличными навыками межличностного взаимодействия. Всегда нацелен на достижение высоких результатов и качественное выполнение задач. Известен своей креативностью и надёжностью.",
            "Проактивный и ориентированный на результат специалист с сильными аналитическими навыками. Легко справляюсь со сложными задачами и всегда ищу новые пути для улучшения. Стремлюсь к постоянному совершенствованию своих навыков."
    );

    // Получить портфолио по id
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        LOGGER.info("Received request to get portfolio with id: " + id + IN_CONTROLLER);
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        LOGGER.info("Returning portfolio with id: " + id + IN_CONTROLLER);
        return ResponseEntity.ok(portfolio);
    }

    // Обновить портфолио
    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolioDetails) {
        LOGGER.info("Received request to update portfolio with id: " + id + IN_CONTROLLER);
        Portfolio updatedPortfolio = portfolioService.updatePortfolio(id, portfolioDetails);
        LOGGER.info("Portfolio updated with id: " + id + IN_CONTROLLER);
        return ResponseEntity.ok(updatedPortfolio);
    }

    // Удалить портфолио
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        LOGGER.info("Received request to delete portfolio with id: " + id + IN_CONTROLLER);
        portfolioService.deletePortfolio(id);
        LOGGER.info("Portfolio deleted with id: " + id + IN_CONTROLLER);
        return ResponseEntity.noContent().build();
    }
}
