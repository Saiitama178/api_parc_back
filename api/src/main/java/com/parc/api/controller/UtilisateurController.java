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

