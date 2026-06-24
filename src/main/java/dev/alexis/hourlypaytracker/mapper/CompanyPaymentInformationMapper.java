package dev.alexis.hourlypaytracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.alexis.hourlypaytracker.dto.CompanyPaymentInformationRequestDto;
import dev.alexis.hourlypaytracker.dto.HourlyRateResponseDto;
import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;

@Mapper(componentModel = "spring")
public interface CompanyPaymentInformationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeEntries", ignore = true)
    CompanyPaymentInformation toEntity(CompanyPaymentInformationRequestDto dto);

    HourlyRateResponseDto toDto(CompanyPaymentInformation entity);
}
