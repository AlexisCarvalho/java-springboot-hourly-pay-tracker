package dev.alexis.hourlypaytracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for time entry responses.
 * Contains time entry data with related entity IDs and calculated duration information.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntryResponseDto {
    /**
     * Date and time when the user clocked in.
     */
    LocalDateTime clockIn;
    
    /**
     * Date and time when the user clocked out.
     */
    LocalDateTime clockOut;
    
    /**
     * Hourly rate for this time entry (calculated from related company payment information).
     */
    BigDecimal hourlyRate;
    
    /**
     * Indicates whether this time entry has been paid.
     */
    Boolean paid;
}