package dev.alexis.hourlypaytracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.alexis.hourlypaytracker.entity.User;

/**
 * Spring Data JPA repository for User entity.
 * Provides database access and custom query methods for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checks if a user with the given code already exists.
     * 
     * @param code User's unique code
     * @return true if user exists, false otherwise
     */
    boolean existsByCode(String code);
    
    /**
     * Finds a user by their unique code.
     * 
     * @param code User's unique code
     * @return Optional containing user if found, empty otherwise
     */
    Optional<User> findByCode(String code);
}
