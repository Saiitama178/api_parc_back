package com.parc.api.service;

import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.model.entity.Utilisateur;
import com.parc.api.model.entity.Validation;
import com.parc.api.model.mapper.UtilisateurMapper;
import com.parc.api.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {

    private  UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ValidationService validationService;


    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateur() {
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        List<UtilisateurDto> utilisateurDtos = utilisateurList.stream()
                .map(UtilisateurMapper::toDto).toList();
        return ResponseEntity.ok(utilisateurDtos);
    }

    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Integer id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            UtilisateurDto utilisateurDto = UtilisateurMapper.toDto(utilisateur.get());
            return ResponseEntity.ok(utilisateurDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        // Vérifie si l'email existe déjà dans la base de données
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateurDto.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà existant");
        }
        // Validation du format de l'email en utilisant une expression régulière
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.+$";
        if (!utilisateurDto.getEmail().matches(emailRegex)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format de l'email invalide");
        }
        // Chiffrement du mot de passe de l'utilisateur
        String mdpCrypte = this.bCryptPasswordEncoder.encode(utilisateurDto.getMdp());
        utilisateurDto.setMdp(mdpCrypte);
        // Mappage du DTO à l'entité
        Utilisateur utilisateur = UtilisateurMapper.toEntity(utilisateurDto);
        // Sauvegarde de l'entité utilisateur dans la base de données
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        // Mappage de l'entité sauvegardée en DTO
        UtilisateurDto savedUtilisateurDto = UtilisateurMapper.toDto(savedUtilisateur);
        // envoi un mail au moment de la création d'un utilisateur
        this.validationService.enregistrer(savedUtilisateur);
        // Retourne une réponse avec le statut créé
        return new ResponseEntity<>(savedUtilisateurDto, HttpStatus.CREATED);

    }

    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        if (utilisateurOptional.isPresent()) {
            utilisateurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurDto utilisateurDto) {
        Optional<Utilisateur> existingUtilisateur = utilisateurRepository.findById(id);
        if (existingUtilisateur.isPresent()) {
            String mdpCrypte = this.bCryptPasswordEncoder.encode(utilisateurDto.getMdp());
            utilisateurDto.setMdp(mdpCrypte);
            Utilisateur utilisateur = existingUtilisateur.get();
            utilisateur.setPseudo(utilisateurDto.getPseudo());
            utilisateur.setEmail(utilisateurDto.getEmail());
            utilisateur.setMdp(utilisateurDto.getMdp());
            utilisateur.setDateCreation(utilisateurDto.getDateCreation());
            utilisateur.setIsActive(utilisateurDto.getIsActive());
            utilisateur = utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok(UtilisateurMapper.toDto(utilisateur));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void  activation(Map<String, String> activation) {
        Validation validation = this.validationService.LireEnFonctionDuCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpire())) {
            throw new RuntimeException("votre code a expiré");
        }
        Utilisateur utilisateurActiver = this.utilisateurRepository.findById(validation.getUtilisateur().getId())
                .orElseThrow(()
                        -> new RuntimeException("utilisateur inconnu"));
        utilisateurActiver.setIsActive(true);
        this.utilisateurRepository.save(utilisateurActiver);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.utilisateurRepository.findByEmail(email)
                .orElseThrow(()
                        -> new UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }
}
