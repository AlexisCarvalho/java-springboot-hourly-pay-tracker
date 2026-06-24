package dev.alexis.hourlypaytracker.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves @CurrentUser annotation to inject authenticated user's ID into controller methods.
 * Extracts the user ID from Spring Security's SecurityContext.
 */
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * Checks if the parameter has @CurrentUser annotation and is of type Long.
     * 
     * @param parameter Method parameter to check
     * @return true if parameter is supported, false otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUser.class) != null 
               && parameter.getParameterType().equals(Long.class);
    }

    /**
     * Resolves the parameter by extracting user ID from SecurityContext.
     * 
     * @param parameter Method parameter to resolve
     * @param mavContainer Model and view container
     * @param webRequest Native web request
     * @param binderFactory Data binder factory
     * @return Authenticated user's ID (Long)
     * @throws IllegalArgumentException if user is not authenticated or principal is invalid
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        // The principal contains the userId (configured in JwtAuthenticationFilter)
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Long)) {
            throw new IllegalArgumentException("Principal deve ser um Long (userId)");
        }

        return principal;
    }
}
