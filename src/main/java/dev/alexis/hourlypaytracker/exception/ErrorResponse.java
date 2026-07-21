package dev.alexis.hourlypaytracker.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Standardized error response DTO for API error responses.
 * Provides consistent error information across all endpoints.
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Timestamp when the error occurred.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    private LocalDateTime timestamp;
    /**
     * HTTP status code.
     */
    private int status;
    /**
     * Error type or category.
     */
    private String error;
    /**
     * Detailed error message.
     */
    private String message;
}