package dev.alexis.hourlypaytracker.config;

import dev.alexis.hourlypaytracker.security.CurrentUserArgumentResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web MVC configuration for the application.
 * Registers custom argument resolvers for annotations.
 */
@Component
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    /**
     * Registers custom argument resolvers for @CurrentUser annotation.
     *
     * @param resolvers List of resolvers to register
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
    }
}
