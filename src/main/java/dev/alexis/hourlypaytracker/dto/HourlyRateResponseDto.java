package dev.alexis.hourlypaytracker.dto;

import java.math.BigDecimal;

import lombok.Value;

// WARNING: Field order matters! This DTO is used in JPQL constructor projections
// (e.g., SELECT new dev/alexis/hourlypaytracker/dto/HourlyRateResponseDto(c.id, c.companyName, c.hourlyRate)).
// Do NOT reorder fields.
@Value
public class HourlyRateResponseDto {
    Long id;
    String companyName;
    BigDecimal hourlyRate;
}
