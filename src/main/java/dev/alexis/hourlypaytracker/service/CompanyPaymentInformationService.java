package dev.alexis.hourlypaytracker.service;

import java.util.List;

import dev.alexis.hourlypaytracker.dto.HourlyRateResponseDto;
import org.springframework.stereotype.Service;

import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;
import dev.alexis.hourlypaytracker.repository.CompanyPaymentInformationRepository;
import jakarta.transaction.Transactional;
import dev.alexis.hourlypaytracker.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CompanyPaymentInformationService {
    private final CompanyPaymentInformationRepository companyPaymentInformationRepository;

    @Transactional
    public CompanyPaymentInformation create(CompanyPaymentInformation companyPaymentInformation) {
        if(companyPaymentInformationRepository.existsByCompanyName(companyPaymentInformation.getCompanyName())) {
            throw new IllegalArgumentException("Company Already Exists");
        }
        
        return companyPaymentInformationRepository.save(companyPaymentInformation);
    }

    public CompanyPaymentInformation getCompanyPaymentInformationWithId(Long id) {
        return companyPaymentInformationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company Payment Information not found"));
    }

    public List<HourlyRateResponseDto> getAllHourlyRates() {
        return companyPaymentInformationRepository.getHourlyRates();
    }

    @Transactional
    public void deleteCompanyPaymentInformationWithId(Long id) {
        companyPaymentInformationRepository.deleteById(id);
    }
}
