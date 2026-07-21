package dev.alexis.hourlypaytracker.dto;

/**
 * Data Transfer Object for JWT token response.
 * Contains the generated JWT token after successful authentication.
 *
 * @param token The JWT token generated for authenticated user.
 *              Valid for 1 hour from generation time.
 */
public record TokenResponseDto(String token) {
}
