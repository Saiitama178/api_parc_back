package com.parc.api;

import com.parc.api.controller.LoginSignUpController;
import com.parc.api.model.dto.AuthentificationDTO;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.service.JwtService;
import com.parc.api.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginSignUpControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginSignUpController loginSignUpController;

    @Test
    void testInscription() {
        // Arrange
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setPseudo("pseudo59");
        utilisateurDto.setEmail("email@example.com");
        utilisateurDto.setMdp("mdp1234");

        UtilisateurDto createdUserDto = new UtilisateurDto();
        createdUserDto.setPseudo("pseudo59");
        createdUserDto.setEmail("email@example.com");
        createdUserDto.setMdp("mdp1234");

        // Configure the mock to return a UtilisateurDto
        when(utilisateurService.createUtilisateur(any(UtilisateurDto.class)))
                .thenReturn(new ResponseEntity<>(createdUserDto, HttpStatus.CREATED));

        // Act
        ResponseEntity<UtilisateurDto> responseEntity = loginSignUpController.inscription(utilisateurDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdUserDto, responseEntity.getBody());  // Compare the objects
    }

    @Test
    void testConnexionSuccess() {
        // Arrange
        AuthentificationDTO authentificationDTO = new AuthentificationDTO("email@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken("email@example.com")).thenReturn("dummyToken");

        // Act
        ResponseEntity<Map<String, String>> responseEntity = loginSignUpController.connexion(authentificationDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Map.of("token", "dummyToken"), responseEntity.getBody());
    }

    @Test
    void testConnexionFailure() {
        // Arrange
        AuthentificationDTO authentificationDTO = new AuthentificationDTO("email@example.com", "wrongPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<Map<String, String>> responseEntity = loginSignUpController.connexion(authentificationDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Map.of("error", "les informations d'identifications sont invalides"), responseEntity.getBody());
    }

    @Test
    void testConnexionException() {
        // Arrange
        AuthentificationDTO authentificationDTO = new AuthentificationDTO("email@example.com", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<Map<String, String>> responseEntity = loginSignUpController.connexion(authentificationDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Map.of("error", "les informations d'identifications sont invalides"), responseEntity.getBody());
    }
}
