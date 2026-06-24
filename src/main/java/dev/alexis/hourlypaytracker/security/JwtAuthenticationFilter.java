package dev.alexis.hourlypaytracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT authentication filter that processes incoming requests.
 * Extracts JWT tokens from request headers and sets up authentication context.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /**
     * Constructs the filter with JWT service dependency.
     * 
     * @param jwtService Service for JWT token operations
     */
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Processes incoming request to extract and validate JWT token.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain Filter chain
     * @throws ServletException if filter processing fails
     * @throws IOException if I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Long userId = jwtService.extractUserIdFromHeader(authHeader);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    new ArrayList<>() // Without roles for now
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            logger.debug("Falha na autenticação JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
