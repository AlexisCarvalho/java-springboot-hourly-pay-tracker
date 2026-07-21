package dev.alexis.hourlypaytracker.repository;

import dev.alexis.hourlypaytracker.entity.TimeEntry;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;

public interface TimeEntryRepositoryCustom {
    List<TimeEntry> findWithCursor(
            Long userId,
            Instant lastClockIn,
            Long lastId,
            Boolean paid,
            int pageSize,
            Sort.Direction direction
    );
}
