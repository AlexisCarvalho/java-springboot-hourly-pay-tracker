package dev.alexis.hourlypaytracker.service;

import org.springframework.stereotype.Service;

import dev.alexis.hourlypaytracker.dto.LoginRequestDto;
import dev.alexis.hourlypaytracker.entity.User;
import dev.alexis.hourlypaytracker.exception.UnauthorizedException;
import dev.alexis.hourlypaytracker.security.JwtService;
import lombok.AllArgsConstructor;

/**
 * Service for authentication business logic.
 * Handles user login and JWT token generation.
 */
@AllArgsConstructor
@Service
public class AuthService {
    
    private final UserService userService;
    private final JwtService jwtService; 

    /**
     * Authenticates a user with the provided credentials and generates a JWT token.
     * 
     * @param dto Login request containing user code and password
     * @return JWT token as a String
     * @throws UnauthorizedException if code or password is invalid
     */
    public String login(LoginRequestDto dto) {
        User user = userService.getUserWithCode(dto.getCode());

        if(user.getPassword().compareTo(dto.getPassword()) == 0) {
            return jwtService.generateToken(user);
        } else { throw new UnauthorizedException("Code or Password Invalid"); }
    }
}
