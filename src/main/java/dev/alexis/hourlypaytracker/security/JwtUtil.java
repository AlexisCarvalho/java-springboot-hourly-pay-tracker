package dev.alexis.hourlypaytracker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${SECRET}")
    private String secret;

    // (1000 * 60 * 60) = 1h in milliseconds
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24h

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generates a JWT token valid for 1 hour.
     *
     * @param userId the user ID
     * @return signed JWT token
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts all claims from the token.
     *
     * @param token the JWT token
     * @return decrypted claims
     * @throws JwtException if token is invalid or expired
     */
    public Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the subject (userId as string) from the token.
     *
     * @param token the JWT token
     * @return user ID as String
     * @throws JwtException if token is invalid
     */
    public String extractSubject(String token) throws JwtException {
        return extractAllClaims(token).getSubject();
    }
}