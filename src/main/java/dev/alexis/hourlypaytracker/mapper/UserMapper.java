package dev.alexis.hourlypaytracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.alexis.hourlypaytracker.dto.UserRequestDto;
import dev.alexis.hourlypaytracker.dto.UserResponseDto;
import dev.alexis.hourlypaytracker.entity.User;

/**
 * MapStruct mapper for User entity and DTO conversions.
 * Automatically generates implementations for mapping between User entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a UserRequestDto to a User entity.
     * 
     * @param dto The user request DTO
     * @return User entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeEntries", ignore = true)
    @Mapping(target = "companyPaymentInformation", ignore = true)
    User toEntity(UserRequestDto dto);

    /**
     * Converts a User entity to a UserResponseDto.
     * 
     * @param entity The user entity
     * @return User response DTO
     */
    UserResponseDto toDto(User entity);
}
