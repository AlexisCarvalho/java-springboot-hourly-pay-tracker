package dev.alexis.hourlypaytracker.controller;

import dev.alexis.hourlypaytracker.dto.BatchOperationWithIdRequest;
import dev.alexis.hourlypaytracker.dto.TimeEntryCursorPaginationResponse;
import dev.alexis.hourlypaytracker.dto.TimeEntryRequestDto;
import dev.alexis.hourlypaytracker.dto.TimeEntryResponseDto;
import dev.alexis.hourlypaytracker.mapper.TimeEntryMapper;
import dev.alexis.hourlypaytracker.security.CurrentUser;
import dev.alexis.hourlypaytracker.service.TimeEntryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * REST controller for time entry operations.
 * All endpoints require JWT authentication.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/time-entry")
public class TimeEntryController {
    private final TimeEntryService timeEntryService;
    private final TimeEntryMapper timeEntryMapper;

    // =================
    // ||    GET     ||
    // =================

    /**
     * Retrieves all time entries for the authenticated user in a specific month.
     *
     * @param userId User ID extracted from JWT token (auto-injected by @CurrentUser)
     * @param year   Year (e.g., 2026)
     * @param month  Month (1-12)
     * @return List of time entry data for the specified month with HTTP 200 status
     * @throws IllegalArgumentException  if month is not between 1-12
     * @throws ResourceNotFoundException if user not found
     */
    @GetMapping("/entries-month")
    public ResponseEntity<List<TimeEntryResponseDto>> getTimeEntriesFromMonth(
            @CurrentUser Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        var timeEntriesDto = timeEntryService.getEntriesFromMonth(userId, year, month);
        return ResponseEntity.ok(timeEntriesDto);
    }

    @GetMapping("/unpaid-entries")
    public ResponseEntity<List<TimeEntryResponseDto>> getAllUnpaidTimeEntries(@CurrentUser Long userId) {
        var timeEntriesDto = timeEntryService.getUnpaidTimeEntriesOfUser(userId);
        return ResponseEntity.ok(timeEntriesDto);
    }

    /**
     * Retrieves all time entries for the authenticated user using global cursor-based pagination.
     *
     * @param userId   User ID extracted from JWT token (auto-injected by @CurrentUser)
     * @param cursor   Optional cursor ID for pagination (null for first page)
     * @param pageSize Number of items per page (default: 10, max: 100)
     * @return CursorPaginationResponse containing paginated time entries with HTTP 200 status
     * @throws IllegalArgumentException if pageSize is invalid
     */
    @GetMapping("/entries-cursor")
    public ResponseEntity<TimeEntryCursorPaginationResponse<TimeEntryResponseDto>> getAllTimeEntriesWithCursor
    (
            @CurrentUser Long userId,
            @RequestParam(required = false) Instant lastClockIn,
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {

        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("pageSize deve estar entre 1 e 100");
        }

        var response = timeEntryService.getAllEntriesWithCursor(userId, lastClockIn, lastId, paid, pageSize, direction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntryResponseDto> getTimeEntryById(@PathVariable("id") Long id) {
        var timeEntryDto = timeEntryService.getTimeEntryWithId(id);
        return ResponseEntity.ok(timeEntryDto);
    }

    // =================
    // ||    POST    ||
    // =================

    /**
     * Creates a new time entry for the authenticated user.
     *
     * @param dto    Time entry data containing clock-in, clock-out times and payment status
     * @param userId User ID extracted from JWT token (auto-injected by @CurrentUser)
     * @return Created time entry data with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<TimeEntryResponseDto> postTimeEntry(@RequestBody @Valid TimeEntryRequestDto dto, @CurrentUser Long userId) {
        var timeEntry = timeEntryService.create(dto, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(timeEntryMapper.toDto(timeEntry));
    }

    // =================
    // ||    PUT     ||
    // =================

    /**
     * Marks multiple time entries as paid.
     *
     * @param dto request containing the IDs of time entries to mark as paid
     * @return 204 No Content response on success
     */
    @PutMapping("/mark-as-paid-by-ids")
    public ResponseEntity<Void> batchUpdate(@RequestBody @Valid BatchOperationWithIdRequest dto) {
        timeEntryService.markAsPaidByIds(dto.getIds());

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTimeEntry(@PathVariable("id") Long id, @RequestBody @Valid TimeEntryRequestDto dto) {
        timeEntryService.updateWithId(id, dto);

        return ResponseEntity
                .noContent()
                .build();
    }

    // =================
    // ||   DELETE   ||
    // =================

    /**
     * Delete multiple time entries.
     *
     * @param dto request containing the IDs of time entries to delete
     * @return 204 No Content response on success
     */
    @DeleteMapping("batch-delete")
    public ResponseEntity<Void> batchDelete(@RequestBody @Valid BatchOperationWithIdRequest dto) {
        timeEntryService.batchDelete(dto.getIds());

        return ResponseEntity
                .noContent()
                .build();
    }
}
