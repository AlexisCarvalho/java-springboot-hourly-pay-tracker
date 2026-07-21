package dev.alexis.hourlypaytracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for time entry responses.
 * Contains time entry data with related entity IDs and calculated duration information.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntryResponseDto {
    Long id;
    /**
     * Date and time when the user clocked in.
     */
    LocalDateTime clockIn;

    /**
     * Date and time when the user clocked out.
     */
    LocalDateTime clockOut;

    /**
     * Indicates whether this time entry has been paid.
     */
    Boolean paid;

    /**
     * Hourly rate information for this time entry.
     */
    @JsonProperty("hourlyRateInformation")
    HourlyRateInformationResponseDto companyPaymentInformation;
}