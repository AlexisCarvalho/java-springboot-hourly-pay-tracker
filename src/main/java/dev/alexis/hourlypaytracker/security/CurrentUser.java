package dev.alexis.hourlypaytracker.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to inject the authenticated user's ID (extracted from the JWT)
 * directly as a parameter in controller methods.
 * <p>
 * Usage:
 *
 * @GetMapping("/profile") public ResponseEntity<UserResponseDto> getProfile(@CurrentUser Long userId) {
 * // userId contains the authenticated user's ID
 * }
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
