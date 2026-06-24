package dev.alexis.hourlypaytracker.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyPaymentInformationRequestDto {
    @NotNull
    private String companyName;

    @NotNull
    @DecimalMin(value = "0.01", message = "Hourly rate must be greater than zero")
    @Digits(integer = 4, fraction = 2, message = "Hourly rate must have up to 4 digits and 2 decimal places")
    private BigDecimal hourlyRate;
}
