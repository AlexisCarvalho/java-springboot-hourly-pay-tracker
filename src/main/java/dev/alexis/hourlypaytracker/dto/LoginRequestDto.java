package dev.alexis.hourlypaytracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for login requests.
 * Contains user credentials for authentication.
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
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
}
