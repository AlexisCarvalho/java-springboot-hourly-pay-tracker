package dev.alexis.hourlypaytracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.alexis.hourlypaytracker.dto.UserResponseDto;
import dev.alexis.hourlypaytracker.entity.User;
import dev.alexis.hourlypaytracker.mapper.UserMapper;
import dev.alexis.hourlypaytracker.security.CurrentUser;
import dev.alexis.hourlypaytracker.service.UserService;
import lombok.AllArgsConstructor;

/**
 * REST controller for user operations.
 * All endpoints require JWT authentication.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    /**
     * Retrieves the profile of the authenticated user.
     * 
     * @param userId User ID extracted from JWT token (auto-injected by @CurrentUser)
     * @return User profile data with HTTP 200 status
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(@CurrentUser Long userId) {
        User user = service.getUserWithId(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.toDto(user));
    }

    /**
     * Retrieves user data by ID (requires authentication).
     * 
     * @param userId User ID to retrieve
     * @return User data with HTTP 200 status
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId) {
        User user = service.getUserWithId(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.toDto(user));
    }
}