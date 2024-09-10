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
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class ConfigurationSecurityApplication {

    private final JwtFilter jwtFilter;
    private final UserLoaderService userLoaderService;
    private final UserAuthorizationManager userAuthorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Routes publiques pour l'authentification et l'inscription
                        .requestMatchers(HttpMethod.POST, "/auth/inscription").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/connexion").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/activation").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/deconnexion").permitAll()

                        // Routes accessibles à tous
                        .requestMatchers(HttpMethod.GET, "/parcs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/parcs/{nomParc}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/periodes").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // Routes accessibles aux visiteurs authentifiés
                        .requestMatchers(HttpMethod.POST, "/commentaires").hasAuthority("Utilisateur")

                        // Routes accessibles aux utilisateurs authentifiés
                        .requestMatchers(HttpMethod.PUT, "/user/{id}")
                        .access(userAuthorizationManager)

                        // Routes accessibles aux administrateurs
                        .requestMatchers("/admin/**").hasAuthority("Administrateur")

                        // Toute autre requête doit être authentifiée
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure()) // Redirige HTTP vers HTTPS
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
