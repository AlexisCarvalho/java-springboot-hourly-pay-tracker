package dev.alexis.hourlypaytracker.mapper;

import dev.alexis.hourlypaytracker.dto.CompanyPaymentInformationRequestDto;
import dev.alexis.hourlypaytracker.dto.HourlyRateInformationResponseDto;
import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyPaymentInformationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeEntries", ignore = true)
    @Mapping(target = "users", ignore = true)
    CompanyPaymentInformation toEntity(CompanyPaymentInformationRequestDto dto);

    HourlyRateInformationResponseDto toDto(CompanyPaymentInformation entity);
}
