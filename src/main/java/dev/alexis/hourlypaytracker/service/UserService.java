package dev.alexis.hourlypaytracker.service;

import dev.alexis.hourlypaytracker.dto.UserRequestDto;
import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;
import dev.alexis.hourlypaytracker.mapper.UserMapper;
import dev.alexis.hourlypaytracker.repository.CompanyPaymentInformationRepository;
import org.springframework.stereotype.Service;

import dev.alexis.hourlypaytracker.entity.User;
import dev.alexis.hourlypaytracker.exception.ResourceNotFoundException;
import dev.alexis.hourlypaytracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * Service for user business logic.
 * Handles user creation, retrieval, and validation.
 */
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CompanyPaymentInformationRepository companyPaymentInformationRepository;
    private final UserMapper userMapper;

    /**
     * Creates a new user in the system.
     * 
     * @param dto User Request DTO to be created
     * @return Created user entity with assigned ID
     * @throws IllegalArgumentException if user code already exists
     */
    @Transactional
    public User create(UserRequestDto dto) {
        if (userRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("User Already Exists");
        }

        User user = userMapper.toEntity(dto);

        if(dto.getPreferredCompanyId() != null) {
            CompanyPaymentInformation companyPaymentInformation = companyPaymentInformationRepository
                    .findById(dto.getPreferredCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Company payment information not found with ID: " + dto.getPreferredCompanyId()
                    ));

            user.setCompanyPaymentInformation(companyPaymentInformation);
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by ID.
     * 
     * @param id User ID to search for
     * @return User entity if found
     * @throws ResourceNotFoundException if user with given ID does not exist
     */
    public User getUserWithId(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Retrieves a user by their unique code.
     * 
     * @param code User code to search for
     * @return User entity if found
     * @throws ResourceNotFoundException if user with given code does not exist
     */
    public User getUserWithCode(String code) {
        return userRepository
                .findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Transactional
    public void deleteUserWithId(Long id) {
        userRepository.deleteById(id);
    }
}
