package dev.alexis.hourlypaytracker.dto;

import lombok.Value;

/**
 * Data Transfer Object for user responses.
 * Contains user data to be returned in API responses (excludes sensitive info like password).
 */
@Value
public class UserResponseDto {
    /**
     * User's unique identifier.
     */
    Long id;
    /**
     * User's full name.
     */
    String name;
    /**
     * User's unique code.
     */
    String code;
}