package com.parc.api.security;

import com.parc.api.security.jwt.JwtFilter;
import com.parc.api.service.UserLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indique que cette classe contient des configurations Spring
@EnableWebSecurity // Active la configuration de la sécurité Web dans Spring
@RequiredArgsConstructor // Génère un constructeur avec tous les champs "final" automatiquement injectés
public class ConfigurationSecurityApplication {

    private final JwtFilter jwtFilter; // Filtre pour la gestion des JWT (tokens d'authentification)
    private final UserLoaderService userLoaderService; // Service pour charger les détails de l'utilisateur (comme les rôles)

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // consider enabling CSRF protection
                .authorizeHttpRequests(authorize -> authorize

                        // Routes publiques
                        .requestMatchers(HttpMethod.POST, "/auth/inscription").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/connexion").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/activation").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/deconnexion").permitAll()

                        // Routes restreintes Administrateur & Visiteur
                        .requestMatchers("/admin/**").hasAuthority("Administrateur")
                        .requestMatchers("/visiteur/**").hasAuthority("Visiteur")
                        .requestMatchers("/parcs").permitAll()
                                // Swagger et API documentation accessibles à tous
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll() // Autorise l'accès public à la documentation API (Swagger)

                                // Toute autre requête doit être authentifiée
                                .anyRequest().authenticated() // Toutes les autres requêtes doivent être authentifiées
                        )
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configure la gestion de session. Les sessions ne sont pas stockées côté serveur (stateless). Requis pour une application avec JWT.
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Ajoute le filtre JWT avant le filtre par défaut d'authentification basé sur le nom d'utilisateur/mot de passe
                        .build(); // Construit et retourne la chaîne de filtres de sécurité
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean pour encoder les mots de passe avec BCrypt, un algorithme de hachage sécurisé.
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Fournit le gestionnaire d'authentification, qui gère les processus d'authentification dans l'application
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return userLoaderService; // Utilise le service "UserLoaderService" pour charger les utilisateurs et leurs rôles
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        // Fournisseur d'authentification qui récupère les utilisateurs et leurs rôles depuis une source de données
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // Associe le UserDetailsService à l'authentification
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        // Associe l'encodeur de mot de passe (BCrypt) pour comparer les mots de passe de manière sécurisée
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        // Retourne le fournisseur d'authentification configuré
        return daoAuthenticationProvider;
    }
}

