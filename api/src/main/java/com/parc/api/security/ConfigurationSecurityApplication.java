package com.parc.api.security;

import com.parc.api.security.jwt.JwtFilter;
import com.parc.api.service.UserLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurityApplication {

    private final JwtFilter jwtFilter;
    private final UserLoaderService userLoaderService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .csrf(AbstractHttpConfigurer::disable) // consider enabling CSRF protection
                        .authorizeHttpRequests(authorize -> authorize

                                // Routes publiques pour inscription, connexion, et activation de compte
                                .requestMatchers(POST, "/auth/inscription").permitAll() // Inscription
                                .requestMatchers(POST, "/auth/connexion").permitAll() // Connexion
                                .requestMatchers(GET, "/auth/activation").permitAll() // Activation de compte

                                // Routes accessibles uniquement aux administrateurs
                                .requestMatchers(POST, "/parcs").hasRole("Administrateur")
                                .requestMatchers(PUT, "/parcs/{id}").hasRole("Administrateur")
                                .requestMatchers(DELETE, "/parcs/{id}").hasRole("Administrateur")
                                .requestMatchers(POST, "/parcs/{id}/images").hasRole("Administrateur")
                                .requestMatchers(DELETE, "/parcs/{parcId}/images/{imageId}").hasRole("Administrateur")
                                .requestMatchers(POST, "/users").hasRole("Administrateur")
                                .requestMatchers(PUT, "/users/{id}").hasRole("Administrateur")
                                .requestMatchers(DELETE, "/users/{id}").hasRole("Administrateur")

                                // Routes accessibles à tous (public) ou aux utilisateurs authentifiés
                                .requestMatchers(GET, "/parcs").permitAll() // Liste des parcs
                                .requestMatchers(GET, "/parcs/{nomParc}").permitAll() // Détails d'un parc
                                .requestMatchers(GET, "/parcs?ville={ville}&region={region}&pays={pays}").permitAll() // Filtrage des parcs
                                .requestMatchers(POST, "/parcs/{id}/commentaires").authenticated() // Commentaires
                                .requestMatchers(POST, "/parcs/{id}/notes").authenticated() // Noter un parc

                                // Swagger et API documentation accessibles à tous
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                                // Toute autre requête doit être authentifiée
                                .anyRequest().authenticated()
                        )
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userLoaderService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
