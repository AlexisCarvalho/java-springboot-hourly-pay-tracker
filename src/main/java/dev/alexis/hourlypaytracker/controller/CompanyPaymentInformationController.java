package dev.alexis.hourlypaytracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.alexis.hourlypaytracker.dto.CompanyPaymentInformationRequestDto;
import dev.alexis.hourlypaytracker.dto.HourlyRateResponseDto;
import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;
import dev.alexis.hourlypaytracker.mapper.CompanyPaymentInformationMapper;
import dev.alexis.hourlypaytracker.service.CompanyPaymentInformationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/company-payment-information")
public class CompanyPaymentInformationController {
    private final CompanyPaymentInformationService companyPaymentInformationService;
    private final CompanyPaymentInformationMapper companyPaymentInformationMapper;

    @PostMapping
    public ResponseEntity<HourlyRateResponseDto> postCompanyPaymentInformation(@RequestBody @Valid CompanyPaymentInformationRequestDto dto) {
        CompanyPaymentInformation companyPaymentInformation = companyPaymentInformationMapper.toEntity(dto);
        CompanyPaymentInformation companyPaymentInformationCreated = companyPaymentInformationService.create(companyPaymentInformation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyPaymentInformationMapper.toDto(companyPaymentInformationCreated));
    }

    @GetMapping("/hourly-rates")
    public ResponseEntity<List<HourlyRateResponseDto>> getAllCompanyPaymentInformation() {
        List<HourlyRateResponseDto> dtoList = companyPaymentInformationService.getAllHourlyRates();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HourlyRateResponseDto> getCompanyPaymentInformationById(@PathVariable("id") Long id) {
        CompanyPaymentInformation company = companyPaymentInformationService.getCompanyPaymentInformationWithId(id);
        return ResponseEntity.ok(companyPaymentInformationMapper.toDto(company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyPaymentInformation(@PathVariable("id") Long id) {
        companyPaymentInformationService.deleteCompanyPaymentInformationWithId(id);
        return ResponseEntity.noContent().build();
    }
}
