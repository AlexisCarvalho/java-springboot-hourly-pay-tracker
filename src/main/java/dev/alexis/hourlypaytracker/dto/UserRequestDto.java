package dev.alexis.hourlypaytracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for user registration requests.
 * Contains data needed to create a new user account.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    /**
     * User's full name.
     */
    @NotNull
    private String name;

    /**
     * User's unique code.
     */
    @NotNull
    private String code;

    /**
     * User's password.
     */
    @NotNull
    private String password;

    private Long preferredCompanyId;
}