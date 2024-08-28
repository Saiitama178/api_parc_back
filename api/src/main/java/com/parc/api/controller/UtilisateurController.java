package com.parc.api.controller;

import com.parc.api.model.dto.AuthentificationDTO;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.service.JwtService;
import com.parc.api.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/utilisateur")
@Tag(name = "utilisateur", description = "Opérations sur les utilisateurs")
public class UtilisateurController {

    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;


    @GetMapping("/user")
    @Operation(summary = "Affiche la liste des utilisateurs",
            description = "Retourne une liste d'utilisateur",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            })
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateur() {
        return this.utilisateurService.getAllUtilisateur();
    }

    @Operation(summary = "Obtenir un utilisateur par ID",
            description = "Retourne un utilisateur spécifique basé sur son ID",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    @GetMapping("/user/{id}")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Integer id) {
        return this.utilisateurService.getUtilisateurById(id);
    }

    @Operation(summary = "Créer un utilisateur",
            description = "Crée un nouvel utilisateur",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Utilisateur créé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            })
    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        return this.utilisateurService.createUtilisateur(utilisateurDto);
    }

    @Operation(summary = "Activer un utilisateur",
            description = "Active un utilisateur basé sur le code d'activation",
            operationId = "utilisateurs/activation",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Activation réussie"),
                    @ApiResponse(responseCode = "400", description = "Code d'activation invalide ou expiré"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    @PostMapping("/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }

    @Operation(summary = "Connexion utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT",
            operationId = "utilisateurs/connexion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentification réussie", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "400", description = "Informations d'identification invalides")
            })
    @PostMapping("/connexion")
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.email(), authentificationDTO.password())
            );

            if (authentication.isAuthenticated()) {

                log.info("Authentification réussie");

                String token = jwtService.generateToken(authentificationDTO.email());

                return ResponseEntity.ok(Map.of("token", token));
            } else {
                log.info("Authentification échouée");
                return ResponseEntity.badRequest().body(Map.of("error", "les informations d'identifications sont invalides "));
            }
        } catch (BadCredentialsException e) {
            log.info("les informations d'identification invalides");
            return ResponseEntity.badRequest().body(Map.of("error", "les informations d'identifications sont invalides"));
        }
    }

    @Operation(summary = "Supprimer un utilisateur",
            description = "Supprime un utilisateur basé sur son ID",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        return this.utilisateurService.deleteUtilisateur(id);
    }

    @Operation(summary = "Mettre à jour un utilisateur",
            description = "Met à jour les informations d'un utilisateur basé sur son ID",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "400", description = "Données invalides"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    @PutMapping("/user/maj/{id}")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurDto utilisateurDto) {
        return this.utilisateurService.updateUtilisateur(id, utilisateurDto);
    }
}

