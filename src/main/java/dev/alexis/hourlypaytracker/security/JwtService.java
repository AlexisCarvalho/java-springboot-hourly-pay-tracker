package dev.alexis.hourlypaytracker.security;

import dev.alexis.hourlypaytracker.entity.User;
import dev.alexis.hourlypaytracker.exception.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

/**
 * Service for JWT token operations.
 * Handles token generation, validation, and user ID extraction.
 */
@Service
public class JwtService {

    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Generates a JWT token for the given user.
     *
     * @param user The user entity
     * @return JWT token as a String
     */
    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getId());
    }

    /**
     * Validates whether a JWT token is valid and not expired.
     *
     * @param token JWT token to validate
     * @return true if token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            jwtUtil.extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token JWT token
     * @return User ID as Long
     * @throws UnauthorizedException if token is invalid or malformed
     */
    public Long extractUserId(String token) {
        try {
            String subject = jwtUtil.extractSubject(token);
            return Long.parseLong(subject);
        } catch (JwtException e) {
            throw new UnauthorizedException("Token inválido ou expirado");
        } catch (NumberFormatException e) {
            throw new UnauthorizedException("Token contém ID de usuário mal formado");
        }
    }

    /**
     * Extracts user ID from an HTTP Authorization header.
     *
     * @param authHeader Authorization header value (e.g., "Bearer token")
     * @return User ID as Long
     * @throws UnauthorizedException if header is missing or malformed
     */
    public Long extractUserIdFromHeader(String authHeader) {
        if (authHeader == null || authHeader.isBlank()) {
            throw new UnauthorizedException("Header Authorization ausente");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Header Authorization deve ser no formato 'Bearer token'");
        }

        String token = authHeader.substring(7).trim();
        return extractUserId(token);
    }

    /**
     * Validates a token and extracts the user ID in one operation.
     *
     * @param token JWT token to validate and extract from
     * @return User ID as Long
     * @throws UnauthorizedException if token is invalid or expired
     */
    public Long validateAndExtractUserId(String token) {
        if (!isTokenValid(token)) {
            throw new UnauthorizedException("Token inválido ou expirado");
        }
        return extractUserId(token);
    }
}