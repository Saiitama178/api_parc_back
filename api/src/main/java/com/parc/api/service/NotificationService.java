package com.parc.api.service;

import com.parc.api.model.entity.Validation;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void envoyer(Validation validation) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("ParcAdvisor@parc.com");
            helper.setTo(validation.getUtilisateur().getEmail());
            helper.setSubject("Votre code d'activation - ParcAdvisor");

            String htmlContent = String.format(
                    "<html>" +
                            "<body>" +
                            "<h2>Bonjour %s,</h2>" +
                            "<p>Votre code d'activation est <strong>%s</strong>.</p>" +
                            "<p>Cliquez sur le lien ci-dessous pour activer votre compte :</p>" +
                            "<p><a href=\"https://example.com/activate/%s\">Activer mon compte</a></p>" +
                            "<p>A bientôt,<br> L'équipe ParcAdvisor</p>" +
                            "</body>" +
                            "</html>",
                    validation.getUtilisateur().getPseudo(),
                    validation.getCode(),
                    validation.getCode()
            );

            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            // Log the error or handle it accordingly
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
