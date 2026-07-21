package dev.alexis.hourlypaytracker.mapper;

import dev.alexis.hourlypaytracker.dto.TimeEntryRequestDto;
import dev.alexis.hourlypaytracker.dto.TimeEntryResponseDto;
import dev.alexis.hourlypaytracker.entity.TimeEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for TimeEntry entity and DTO conversions.
 * Automatically generates implementations for mapping between TimeEntry entities and DTOs.
 * <p>
 * Note: Request DTO to Entity conversion is handled by TimeEntryService to maintain
 * business logic separation and validate entity relationships.
 */
@Mapper(componentModel = "spring", uses = {CompanyPaymentInformationMapper.class})
public interface TimeEntryMapper {

    /**
     * Converts a TimeEntry entity to a TimeEntryResponseDto.
     * Maps related entity IDs for response.
     *
     * @param entity The time entry entity
     * @return TimeEntry response DTO
     */
    TimeEntryResponseDto toDto(TimeEntry entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "companyPaymentInformation", ignore = true)
    TimeEntry toEntity(TimeEntryRequestDto dto);
}
