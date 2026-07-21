package dev.alexis.hourlypaytracker.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * Converts exceptions into standardized HTTP responses with error details.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds a standardized error response.
     *
     * @param status  HTTP status code
     * @param title   Error title
     * @param message Error message
     * @return Response entity with error details
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String title, String message) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                title,
                message
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "The request body is malformed or contains an invalid value.";

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormatEx) {
            String fieldName = invalidFormatEx.getPath().isEmpty()
                    ? "unknown"
                    : invalidFormatEx.getPath().getLast().getFieldName();

            message = "Value '" + invalidFormatEx.getValue() + "' is invalid for field '" + fieldName + "'.";
        }

        if (cause instanceof MismatchedInputException mismatchedEx && !(cause instanceof InvalidFormatException)) {
            String fieldName = mismatchedEx.getPath().isEmpty()
                    ? "unknown"
                    : mismatchedEx.getPath().getLast().getFieldName();
            message = "Required field '" + fieldName + "' is missing or invalid.";
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Value '" + ex.getValue() + "' is invalid for parameter '" + ex.getName() + "'.";

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", message);
    }

    /**
     * Handles IllegalArgumentException.
     *
     * @param ex The exception thrown
     * @return Error response with HTTP 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex The exception thrown
     * @return Error response with HTTP 404 status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage());
    }

    /**
     * Handles UnauthorizedException.
     *
     * @param ex The exception thrown
     * @return Error response with HTTP 401 status
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }

    /**
     * Handles ForbiddenException.
     *
     * @param ex The exception thrown
     * @return Error response with HTTP 403 status
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage());
    }

    /**
     * Handles request validation failures.
     *
     * @param ex The exception thrown
     * @return Error response with HTTP 400 status and validation details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation errors detected");

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message);
    }

    /**
     * Handles data integrity violation failures.
     *
     * @param ex The exception thrown
     * @return Error response with HTTP 409 status
     * @apiNote Don't return a detailed message to not exposed backend logic
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Data integrity violation for request. message={}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                "Data integrity restrictions are violated by the operation"
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
}