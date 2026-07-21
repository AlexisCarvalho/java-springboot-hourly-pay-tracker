package dev.alexis.hourlypaytracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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

    @Column(nullable = false, unique = true)
    private String companyName;

    @Column(precision = 6, scale = 2)
    private BigDecimal hourlyRate;

    @OneToMany(mappedBy = "companyPaymentInformation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntry> timeEntries;

    @OneToMany(mappedBy = "companyPaymentInformation", fetch = FetchType.LAZY)
    private List<User> users;
}