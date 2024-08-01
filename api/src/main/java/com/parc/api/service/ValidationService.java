package com.parc.api.service;

import com.parc.api.model.entity.Utilisateur;
import com.parc.api.model.entity.Validation;
import com.parc.api.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@AllArgsConstructor
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public void enregistrer(Utilisateur utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expriation = creation.plus(20, MINUTES);
        validation.setExpire(expriation);
        Random random = new Random();
        int randomInt = random.nextInt(9999999);
        String code = String.format("%06d", randomInt);

        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation LireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("code valide"));
    }
}
