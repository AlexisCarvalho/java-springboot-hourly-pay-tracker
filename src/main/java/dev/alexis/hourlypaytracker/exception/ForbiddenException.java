package dev.alexis.hourlypaytracker.exception;

/**
 * Exception thrown when a user lacks permission to access a resource.
 * Results in HTTP 403 Forbidden response.
 */
public class ForbiddenException extends RuntimeException {
    /**
     * Constructs exception with error message.
     *
     * @param message Descriptive error message
     */
    public ForbiddenException(String message) {
        super(message);
    }
}
