package dev.alexis.hourlypaytracker.dto;

import lombok.Value;

/**
 * Data Transfer Object for JWT token response.
 * Contains the generated JWT token after successful authentication.
 */
@Value
public class TokenResponseDto {
    /**
     * The JWT token generated for authenticated user.
     * Valid for 1 hour from generation time.
     */
    String token;
}
