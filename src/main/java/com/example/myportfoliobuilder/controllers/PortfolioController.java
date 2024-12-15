package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.dto.*;
import com.example.myportfoliobuilder.models.Education;
import com.example.myportfoliobuilder.models.Portfolio;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.models.Work;
import com.example.myportfoliobuilder.repositories.EducationRepository;
import com.example.myportfoliobuilder.repositories.UserRepository;
import com.example.myportfoliobuilder.repositories.WorkRepository;
import com.example.myportfoliobuilder.services.PortfolioService;
import com.example.myportfoliobuilder.services.UserService;
import com.example.myportfoliobuilder.services.WorkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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
    private UserRepository userRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private EducationRepository educationRepository;

    @PostMapping
    public ResponseEntity<String> createPortfolio(@RequestBody PortfolioFormDTO formDTO) throws IOException {
        Optional<User> optionalUser = userRepository.findByEmail(formDTO.getUserEmail());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not present " + formDTO.getUserEmail() + formDTO.getFirstName());
        }
        User user = optionalUser.get();
        user.setName(formDTO.getFirstName());
        user.setSurname(formDTO.getLastName());
        user.setBirthDate(formDTO.getDob());
        user.setCountry(formDTO.getCountry());
        user.setPhoneNumber(formDTO.getPhone());
        user.setEmail(formDTO.getUserEmail());
        user.setGender(formDTO.getGender());
        user.setBusinessTrips(formDTO.getBusinessTrips());
        user.setEmploymentType(formDTO.getEmployment());
        user.setWorkMode(formDTO.getWorkMode());
        userRepository.save(user);
        // Сохраняем фотографию
        /*if (formDTO.getPhoto() != null && !formDTO.getPhoto().isEmpty()) {
            MultipartFile photoFile = formDTO.getPhoto();
            Photo photo = new Photo();
            photo.setData(photoFile.getBytes());
            photo.setUser(user);
            user.setPhoto(photo);
        }*/

        if (formDTO.getWorks() != null) {
            for (WorkDTO workDTO : formDTO.getWorks()) {
                Work work = new Work();
                work.setPosition(workDTO.getPosition());
                work.setCompany(workDTO.getCompany());
                work.setCity(workDTO.getCity());
                work.setStartDate(workDTO.getStartDate());
                work.setEndDate(workDTO.getEndDate());
                work.setJobsInfo(workDTO.getJobsInfo());
                work.setUser(user);

                workRepository.save(work);
            }
        }

        // Обрабатываем и сохраняем работы
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

                educationRepository.save(education);
            }
        }

        return ResponseEntity.ok("Portfolio created successfully");
    }

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> generateUserPortfolio(@RequestParam String email) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found with email: " + email));
        }

        User user = userOptional.get();
        Random random = new Random();
        Map<String, Object> portfolioData = Map.of(
                "header", HEADERS.get(random.nextInt(HEADERS.size())),
                "name", user.getName(),
                "surname", user.getSurname(),
                "email", user.getEmail(),
                "phoneNumber", user.getPhoneNumber(),
                "businessTrips", user.getBusinessTrips(),
                "employmentType", user.getEmploymentType(),
                "workSchedule", user.getWorkMode(),
                "citizenship", user.getCitizenship(),
                "country", user.getCountry()
        );

        return ResponseEntity.ok(portfolioData);
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

    // Получить список всех портфолио
    @GetMapping
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        LOGGER.info("Received request to get all portfolios " + IN_CONTROLLER);
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        LOGGER.info("Returning " + portfolios.size() + " portfolios" + IN_CONTROLLER);
        return ResponseEntity.ok(portfolios);
    }

    // Получить портфолио по id
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        LOGGER.info("Received request to get portfolio with id: " + id + IN_CONTROLLER);
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        LOGGER.info("Returning portfolio with id: " + id + IN_CONTROLLER);
        return ResponseEntity.ok(portfolio);
    }

    // Создать новое портфолио
    /*@PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody PortfolioRequestDTO portfolioRequest) {
        LOGGER.info("Received request to create a new portfolio for user id: " + portfolioRequest.getUserId() + IN_CONTROLLER);
        Portfolio savedPortfolio = portfolioService.createPortfolio(portfolioRequest);
        LOGGER.info("Portfolio created with id: " + savedPortfolio.getId() + IN_CONTROLLER);
        return ResponseEntity.ok(savedPortfolio);
    }*/

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
