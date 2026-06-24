package dev.alexis.hourlypaytracker.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for time entry creation requests.
 * Contains clock-in/out times and payment status.
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeEntryRequestDto {
    @NotNull
    Long companyPaymentInformationId;

    /**
     * Date and time when the user clocked in.
     * Format: yyyy-MM-dd'T'HH:mm:ss
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime clockIn;

    /**
     * Date and time when the user clocked out.
     * Format: yyyy-MM-dd'T'HH:mm:ss
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime clockOut;

    /**
     * Indicates whether this time entry has been paid.
     */
    @NotNull
    Boolean paid;

}