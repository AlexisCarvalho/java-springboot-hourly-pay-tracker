package dev.alexis.hourlypaytracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a user entity in the system.
 * Stores user credentials and maintains a one-to-many relationship with TimeEntry.
 */
@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Unique identifier for the user (auto-generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's full name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * User's unique username/code used for authentication.
     */
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * User's hashed password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * List of time entries created by this user.
     * Loaded lazily to improve performance.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntry> timeEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_payment_information_id")
    private CompanyPaymentInformation companyPaymentInformation;
}