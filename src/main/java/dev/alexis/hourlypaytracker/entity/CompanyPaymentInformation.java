package dev.alexis.hourlypaytracker.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_payment_informations")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyPaymentInformation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique =true)
    private String companyName;

    @Column(precision = 6, scale = 2)
    private BigDecimal hourlyRate;

    @OneToMany(mappedBy = "companyPaymentInformation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntry> timeEntries;

    @OneToMany(mappedBy = "companyPaymentInformation", fetch = FetchType.LAZY)
    private List<User> users;
}