package dev.alexis.hourlypaytracker.controller;

import dev.alexis.hourlypaytracker.dto.CompanyPaymentInformationRequestDto;
import dev.alexis.hourlypaytracker.dto.HourlyRateInformationResponseDto;
import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;
import dev.alexis.hourlypaytracker.mapper.CompanyPaymentInformationMapper;
import dev.alexis.hourlypaytracker.service.CompanyPaymentInformationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/company-payment-information")
public class CompanyPaymentInformationController {
    private final CompanyPaymentInformationService companyPaymentInformationService;
    private final CompanyPaymentInformationMapper companyPaymentInformationMapper;

    // =================
    // ||    GET     ||
    // =================

    @GetMapping("/hourly-rates")
    public ResponseEntity<List<HourlyRateInformationResponseDto>> getAllCompanyPaymentInformation() {
        List<HourlyRateInformationResponseDto> dtoList = companyPaymentInformationService.getAllHourlyRates();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HourlyRateInformationResponseDto> getCompanyPaymentInformationById(@PathVariable("id") Long id) {
        CompanyPaymentInformation company = companyPaymentInformationService.getCompanyPaymentInformationWithId(id);
        return ResponseEntity.ok(companyPaymentInformationMapper.toDto(company));
    }

    // =================
    // ||    POST    ||
    // =================

    @PostMapping
    public ResponseEntity<HourlyRateInformationResponseDto> postCompanyPaymentInformation(@RequestBody @Valid CompanyPaymentInformationRequestDto dto) {
        CompanyPaymentInformation companyPaymentInformation = companyPaymentInformationMapper.toEntity(dto);
        CompanyPaymentInformation companyPaymentInformationCreated = companyPaymentInformationService.create(companyPaymentInformation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyPaymentInformationMapper.toDto(companyPaymentInformationCreated));
    }

    // =================
    // ||   DELETE   ||
    // =================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyPaymentInformation(@PathVariable("id") Long id) {
        companyPaymentInformationService.deleteCompanyPaymentInformationWithId(id);
        return ResponseEntity.noContent().build();
    }
}
