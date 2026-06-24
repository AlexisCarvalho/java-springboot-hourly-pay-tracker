package dev.alexis.hourlypaytracker.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation para injetar o ID do usuário autenticado (extraído do JWT)
 * diretamente como parâmetro em métodos de controller.
 *
 * Uso:
 *   @GetMapping("/profile")
 *   public ResponseEntity<UserResponseDto> getProfile(@CurrentUser Long userId) {
 *       // userId contém o ID do usuário autenticado
 *   }
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
