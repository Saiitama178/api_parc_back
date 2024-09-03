package com.parc.api.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.function.Function;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenBlacklistService tokenBlacklistService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private long jwtExpiration;

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    public String createToken(Map<String, Object> claims, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT secret key: " + e.getMessage());
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, String email) {
        final String extractedUsername = extractEmail(token);
        return (extractedUsername.equals(email) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Gérer les jetons expirés ici
            throw new RuntimeException("JWT token has expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            // Gérer les autres erreurs JWT ici
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Date extractExpirationDate(String token) {
        return extractExpiration(token);
    }

}
