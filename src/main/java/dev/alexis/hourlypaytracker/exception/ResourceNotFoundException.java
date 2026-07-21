package dev.alexis.hourlypaytracker.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Results in HTTP 404 Not Found response.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs exception with error message.
     *
     * @param message Descriptive error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
