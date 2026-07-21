package dev.alexis.hourlypaytracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Generic response DTO for cursor-based pagination.
 * Contains a list of items and the next cursor for fetching additional pages.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntryCursorPaginationResponse<T> {

    private List<T> items;

    private Long nextLastId;

    private Instant nextLastClockIn;

    private boolean hasMore;
}
