package dev.alexis.hourlypaytracker.dto;

import java.math.BigDecimal;

// WARNING: Field order matters! This DTO is used in JPQL constructor projections
// (e.g., SELECT new dev/alexis/hourlypaytracker/dto/HourlyRateInformationResponseDto(c.id, c.companyName, c.hourlyRate)).
// Do NOT reorder fields.
public record HourlyRateInformationResponseDto(Long id, String companyName, BigDecimal hourlyRate) {
}
