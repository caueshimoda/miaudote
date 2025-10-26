package com.miaudote.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class EmailService {

     @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    // criar um controller não é necessário para uma classe simples

    public String sendEmail(String email) {
        try {
            // Lê o conteúdo HTML do template no classpath
            String htmlContent;
            try (var inputStream = Objects.requireNonNull(
                    EmailService.class.getResourceAsStream("/templates/email-content.html"),
                    "Template de e-mail não encontrado em /templates/email-content.html"
            )) {
                htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            // Substitui referência à imagem local pela URL pública do Render
            htmlContent = htmlContent.replace(
                "cid:Miaudotefinal1.png",
                "https://miaudote-8av5.onrender.com/img/Miaudotefinal1.png"
            );

            // Monta e envia o e-mail via SendGrid
            Email from = new Email("miaudote.es3@gmail.com", "Equipe MiAudote");
            Email to = new Email(email);
            String subject = "Bem-vindo(a) ao MiAudote!";
            Content content = new Content("text/html", htmlContent);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                return "success";
            } else {
                return "Erro: " + response.getStatusCode() + " - " + response.getBody();
            }

        } catch (IOException e) {
            return "Erro ao enviar e-mail via SendGrid: " + e.getMessage();
        } catch (Exception e) {
            return "Erro geral ao processar envio de e-mail: " + e.getMessage();
        }

    }

}
