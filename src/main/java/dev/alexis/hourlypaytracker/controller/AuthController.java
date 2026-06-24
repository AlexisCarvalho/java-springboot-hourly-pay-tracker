package dev.alexis.hourlypaytracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.alexis.hourlypaytracker.dto.LoginRequestDto;
import dev.alexis.hourlypaytracker.dto.TokenResponseDto;
import dev.alexis.hourlypaytracker.dto.UserRequestDto;
import dev.alexis.hourlypaytracker.dto.UserResponseDto;
import dev.alexis.hourlypaytracker.entity.User;
import dev.alexis.hourlypaytracker.mapper.UserMapper;
import dev.alexis.hourlypaytracker.service.AuthService;
import dev.alexis.hourlypaytracker.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * REST controller for authentication operations.
 * Handles user registration and login endpoints.
 * These endpoints are publicly accessible without authentication.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;

    /**
     * Registers a new user in the system.
     * 
     * @param dto User registration data containing name, code, and password
     * @return Created user data with HTTP 201 status
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto dto) {
        User userCreated = userService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toDto(userCreated));
    }

    /**
     * Authenticates a user and generates a JWT token.
     * 
     * @param dto Login credentials containing code and password
     * @return JWT token with HTTP 200 status
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        String token = authService.login(dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new TokenResponseDto(token));
    }
}
