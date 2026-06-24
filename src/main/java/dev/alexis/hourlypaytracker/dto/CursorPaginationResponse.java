package dev.alexis.hourlypaytracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Generic response DTO for cursor-based pagination.
 * Contains a list of items and the next cursor for fetching additional pages.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursorPaginationResponse<T> {
    /**
     * List of items for the current page.
     */
    private List<T> items;

    /**
     * Cursor value to fetch the next page.
     * Null if no more items are available.
     */
    private Long nextCursor;

    /**
     * Indicates whether there are more items available after this page.
     */
    private Boolean hasMore;
}
