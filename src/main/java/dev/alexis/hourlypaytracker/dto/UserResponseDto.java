package dev.alexis.hourlypaytracker.dto;

/**
 * Data Transfer Object for user responses.
 * Contains user data to be returned in API responses (excludes sensitive info like password).
 *
 * @param id   User's unique identifier.
 * @param name User's full name.
 * @param code User's unique code.
 */
public record UserResponseDto(Long id, String name, String code) {
}