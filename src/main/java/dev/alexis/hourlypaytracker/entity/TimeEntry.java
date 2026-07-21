package dev.alexis.hourlypaytracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a time entry record for a user.
 * Tracks clock-in and clock-out times, and payment status.
 */
@Entity
@Table(name = "time_entries")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntry {

    /**
     * Unique identifier for the time entry (auto-generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who created this time entry.
     * Required relationship with User entity.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_payment_information_id", nullable = false)
    private CompanyPaymentInformation companyPaymentInformation;

    /**
     * The date and time when the user clocked in.
     */
    @Column(name = "clock_in", nullable = false)
    private LocalDateTime clockIn;

    /**
     * The date and time when the user clocked out.
     */
    @Column(name = "clock_out", nullable = false)
    private LocalDateTime clockOut;

    /**
     * Indicates whether this time entry has been paid.
     */
    @Column(nullable = false)
    private Boolean paid;
}