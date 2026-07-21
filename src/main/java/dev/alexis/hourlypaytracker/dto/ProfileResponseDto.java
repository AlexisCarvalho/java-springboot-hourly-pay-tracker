package dev.alexis.hourlypaytracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for user responses.
 * Contains user data to be returned in API responses (excludes sensitive info like password).
 *
 * @param id                        User's unique identifier.
 * @param name                      User's full name.
 * @param code                      User's unique code.
 * @param companyPaymentInformation Hourly rate information for this user.
 */
public record ProfileResponseDto(Long id, String name, String code,
                                 @JsonProperty("hourlyRateInformation") HourlyRateInformationResponseDto companyPaymentInformation) {
}