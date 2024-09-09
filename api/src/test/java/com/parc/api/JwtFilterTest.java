package com.parc.api;

import com.parc.api.security.jwt.JwtFilter;
import com.parc.api.service.JwtService;
import com.parc.api.service.TokenBlacklistService;
import com.parc.api.service.UserLoaderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {
    @Mock
    private JwtService jwtService;
    @Mock
    private UserLoaderService userLoaderService;
    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        jwtFilter = new JwtFilter(jwtService, userLoaderService, tokenBlacklistService);
    }
    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        String validToken = "valid_token";
        String email = "testuser@example.com";// TODO: Implement this test

        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(jwtService.extractEmail(validToken)).thenReturn(email);
        when(tokenBlacklistService.isTokenBlacklisted(validToken)).thenReturn(false);
        when(userLoaderService.loadUserByUsername(email)).thenReturn(mock(UserDetails.class));
        when(jwtService.validateToken(validToken, email)).thenReturn(true);

        jwtFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);



    }}
