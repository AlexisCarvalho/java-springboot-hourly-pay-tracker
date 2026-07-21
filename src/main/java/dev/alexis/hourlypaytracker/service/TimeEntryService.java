package dev.alexis.hourlypaytracker.service;

import dev.alexis.hourlypaytracker.dto.TimeEntryCursorPaginationResponse;
import dev.alexis.hourlypaytracker.dto.TimeEntryRequestDto;
import dev.alexis.hourlypaytracker.dto.TimeEntryResponseDto;
import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;
import dev.alexis.hourlypaytracker.entity.TimeEntry;
import dev.alexis.hourlypaytracker.entity.User;
import dev.alexis.hourlypaytracker.exception.ResourceNotFoundException;
import dev.alexis.hourlypaytracker.exception.UnauthorizedException;
import dev.alexis.hourlypaytracker.mapper.TimeEntryMapper;
import dev.alexis.hourlypaytracker.repository.CompanyPaymentInformationRepository;
import dev.alexis.hourlypaytracker.repository.TimeEntryRepository;
import dev.alexis.hourlypaytracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Service for time entry business logic.
 * Handles time entry creation and retrieval.
 */
@AllArgsConstructor
@Service
@Transactional
public class TimeEntryService {
    private final TimeEntryRepository timeEntryRepository;
    private final UserRepository userRepository;
    private final CompanyPaymentInformationRepository companyPaymentInformationRepository;
    private final TimeEntryMapper timeEntryMapper;

    /**
     * Creates a new time entry for the specified user.
     * Loads related entities and validates that user has permission to create entry for the given company.
     *
     * @param dto                 Time entry request DTO with clock-in, clock-out, and payment status
     * @param authenticatedUserId ID of the authenticated user (from JWT)
     * @return Created time entry entity with assigned ID
     * @throws UnauthorizedException     if user cannot create entry for the specified company
     * @throws ResourceNotFoundException if user or company payment information not found
     */
    @Transactional
    public TimeEntry create(TimeEntryRequestDto dto, Long authenticatedUserId) {
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CompanyPaymentInformation companyPaymentInformation = companyPaymentInformationRepository
                .findById(dto.getCompanyPaymentInformationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company payment information not found with ID: " + dto.getCompanyPaymentInformationId()
                ));

        TimeEntry timeEntry = timeEntryMapper.toEntity(dto);
        timeEntry.setUser(user);
        timeEntry.setCompanyPaymentInformation(companyPaymentInformation);

        return timeEntryRepository.save(timeEntry);
    }

    @Transactional
    public List<TimeEntryResponseDto> getEntriesFromMonth(Long userId, int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Mês inválido: " + month);
        }

        LocalDateTime start = LocalDateTime.of(
                year,
                month,
                1,
                0,
                0,
                0
        );

        LocalDateTime end = start.plusMonths(1);

        List<TimeEntry> timeEntries = timeEntryRepository.timeEntriesFromPeriod(
                userId,
                start,
                end
        );

        return timeEntries.stream()
                .map(timeEntryMapper::toDto)
                .toList();
    }

    // Truncated to find only unpaid entries, since paid entries typically form a large history; it's better to fetch them via a cursor when needed.
    @Transactional
    public List<TimeEntryResponseDto> getUnpaidTimeEntriesOfUser(Long userId) {
        var entries = timeEntryRepository.findByUserIdAndPaidOrderByClockIn(userId, false);
        return entries.stream().map(timeEntryMapper::toDto).toList();
    }

    @Transactional
    public TimeEntryResponseDto getTimeEntryWithId(Long id) {
        TimeEntry timeEntry = timeEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time Entry Not Found"));

        return timeEntryMapper.toDto(timeEntry);
    }

    /**
     * Marks multiple time entries as paid in a single database update.
     *
     * @param ids the IDs of the time entries to mark as paid
     * @throws ResourceNotFoundException with rollback if **any** of the specified time entries do not exist in the database
     */
    @Transactional
    public void markAsPaidByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("IDs list cannot be null or empty");
        }

        int updated = timeEntryRepository.updateMultipleToPaid(ids);

        if (updated != ids.size()) {
            throw new ResourceNotFoundException(
                    "Batch update failed: expected to update " + ids.size() +
                            " time entries, but only " + updated +
                            " were found. The operation was rolled back and no records were updated."
            );
        }
    }

    @Transactional
    public void updateWithId(Long id, TimeEntryRequestDto dto) {
        CompanyPaymentInformation cpi = companyPaymentInformationRepository.getReferenceById(dto.getCompanyPaymentInformationId());
        int updated = timeEntryRepository.updateWithId(id, dto.getClockIn(), dto.getClockOut(), dto.getPaid(), cpi);

        if (updated == 0) {
            throw new ResourceNotFoundException("Time Entry not found");
        }
    }

    /**
     * Deletes multiple time entries in a single database operation.
     *
     * @param ids the IDs of the time entries to delete
     * @throws ResourceNotFoundException with rollback if **any** of the specified time entries do not exist in the database
     */
    @Transactional
    public void batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("IDs list cannot be null or empty");
        }

        int deleted = timeEntryRepository.deleteByIds(ids);

        if (deleted != ids.size()) {
            throw new ResourceNotFoundException(
                    "Batch delete failed: expected to delete " + ids.size() +
                            " time entries, but only " + deleted +
                            " were found. The operation was rolled back and no records were deleted."
            );
        }
    }

    /**
     * Retrieves all time entries for the authenticated user using cursor-based pagination (global).
     *
     * @param userId   User ID extracted from JWT token
     * @param cursor   ID cursor for pagination (null for first page)
     * @param pageSize Number of items per page
     * @return CursorPaginationResponse containing entries, next cursor, and hasMore flag
     */
    public TimeEntryCursorPaginationResponse<TimeEntryResponseDto> getAllEntriesWithCursor
    (
            Long userId,
            Instant lastClockIn,
            Long lastId,
            Boolean paid,
            int pageSize,
            Sort.Direction direction
    ) {

        // Fetch pageSize + 1 items to check if there are more
        List<TimeEntry> entries = timeEntryRepository.findWithCursor
                (
                        userId,
                        lastClockIn,
                        lastId,
                        paid,
                        pageSize,
                        direction
                );

        // Limit to requested page size and check if there are more items
        boolean hasMore = entries.size() > pageSize;

        List<TimeEntry> page = entries.stream()
                .limit(pageSize)
                .toList();

        Long nextLastId = null;
        Instant nextLastClockIn = null;

        if (hasMore) {

            TimeEntry last = page.get(page.size() - 1);

            nextLastId = last.getId();
            nextLastClockIn = last.getClockIn().toInstant(ZoneOffset.UTC);
        }

        return TimeEntryCursorPaginationResponse.<TimeEntryResponseDto>builder()
                .items(page.stream().map(timeEntryMapper::toDto).toList())
                .nextLastId(nextLastId)
                .nextLastClockIn(nextLastClockIn)
                .hasMore(hasMore)
                .build();
    }
}
