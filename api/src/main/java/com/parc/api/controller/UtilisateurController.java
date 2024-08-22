package com.parc.api.controller;

import com.parc.api.model.dto.AuthentificationDTO;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UtilisateurController {

    private AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user")
    @Operation(summary = "Affiche la liste des utilisateurs", description = "Retourne une liste d'utilisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Liste utilisateur")
            })
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateur() {
        return this.utilisateurService.getAllUtilisateur();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Integer id) {
        return this.utilisateurService.getUtilisateurById(id);
    }

    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        return this.utilisateurService.createUtilisateur(utilisateurDto);
    }

    @PostMapping("/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }

    @PostMapping("/connexion")
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.email(), authentificationDTO.password())
            );

            if (authentication.isAuthenticated()) {
                //this.jwtService.generate(authentificationDTO.email());
                log.info("Authentification réussie");
                return ResponseEntity.ok(Map.of("message", "Authentification réussie"));
            } else {
                log.info("Authentification échouée");
                return ResponseEntity.badRequest().body(Map.of("error", "les informations d'identification sont invalides "));
            }
        } catch (BadCredentialsException e) {
            log.info("les informations d'identification invalides");
            return ResponseEntity.badRequest().body(Map.of("error", "les informations d'identification sont invalides"));
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        return this.utilisateurService.deleteUtilisateur(id);
    }

    @PutMapping("/user/maj/{id}")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurDto utilisateurDto) {
        return this.utilisateurService.updateUtilisateur(id, utilisateurDto);
    }
}

