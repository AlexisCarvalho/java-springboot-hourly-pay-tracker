package dev.alexis.hourlypaytracker.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexis.hourlypaytracker.exception.ErrorResponse;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Custom authentication entry point for handling authentication failures.
 * Returns JSON formatted 401 Unauthorized response instead of default 403 Forbidden.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Handles authentication errors by returning a standardized JSON error response.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @param authException The authentication exception
     * @throws IOException if I/O error occurs
     * @throws ServletException if servlet error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
                        AuthenticationException authException) throws IOException, ServletException {
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpServletResponse.SC_UNAUTHORIZED,
            "Unauthorized",
            "Invalid or missing JWT token"
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
