package dev.alexis.hourlypaytracker.exception;

/**
 * Exception thrown when authentication or authorization fails.
 * Results in HTTP 401 Unauthorized response.
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs exception with error message.
     * 
     * @param message Descriptive error message
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
