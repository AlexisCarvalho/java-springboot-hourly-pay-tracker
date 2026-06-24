package dev.alexis.hourlypaytracker.repository;

import dev.alexis.hourlypaytracker.dto.HourlyRateResponseDto;
import dev.alexis.hourlypaytracker.dto.TimeEntryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;

import java.util.List;

@Repository
public interface CompanyPaymentInformationRepository extends JpaRepository<CompanyPaymentInformation, Long> {
    boolean existsByCompanyName(String companyName);

    @Query("SELECT new dev.alexis.hourlypaytracker.dto.HourlyRateResponseDto(c.id, c.companyName, c.hourlyRate) " +
            "FROM CompanyPaymentInformation c")
    List<HourlyRateResponseDto> getHourlyRates();
}
