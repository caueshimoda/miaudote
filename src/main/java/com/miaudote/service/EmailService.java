package com.miaudote.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // criar um controller não é necessário para uma classe simples

    public String sendEmail(String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("miaudote.es3@gmail.com");
            helper.setTo(email);
            helper.setSubject("Bem-vindo(a) ao MiAudote!");

            // Lê o HTML do classpath
            try (var inputStream = Objects.requireNonNull(
                    EmailService.class.getResourceAsStream("/templates/email-content.html")
            )) {
                String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                helper.setText(htmlContent, true);
            }

            // Adiciona a imagem inline
            ClassPathResource image = new ClassPathResource("static/Miaudotefinal1.png");
            helper.addInline("Miaudotefinal1.png", image);

            mailSender.send(message);

            return "success";

        } catch (Exception e) {
            return e.getMessage();

        }

    }

}
