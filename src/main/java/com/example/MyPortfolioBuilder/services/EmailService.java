package com.example.MyPortfolioBuilder.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String createVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public void sendVerificationEmail(String recipientEmail, String token) {
        // Сформировать ссылку для подтверждения
        String verificationLink = "http://localhost:8080/api/users/verify-email?token=" + token;

        // Создать письмо
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("Подтверждение email");
            helper.setText(
                    "<p>Здравствуйте,</p>" +
                            "<p>Пожалуйста, подтвердите ваш email, перейдя по следующей ссылке:</p>" +
                            "<a href=\"" + verificationLink + "\">Подтвердить email</a>" +
                            "<p>Если вы не регистрировались, проигнорируйте это письмо.</p>",
                    true // Это HTML-сообщение
            );

            // Отправка письма
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Не удалось отправить письмо подтверждения", e);
        }
    }
}