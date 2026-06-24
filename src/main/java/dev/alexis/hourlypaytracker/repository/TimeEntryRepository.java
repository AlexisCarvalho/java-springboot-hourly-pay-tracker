package dev.alexis.hourlypaytracker.repository;

import java.time.LocalDateTime;
import java.util.List;

import dev.alexis.hourlypaytracker.entity.CompanyPaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.alexis.hourlypaytracker.entity.TimeEntry;
import jakarta.transaction.Transactional;

/**
 * Spring Data JPA repository for TimeEntry entity.
 * Provides database access and custom query methods for TimeEntry entities.
 */
@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    /**
     * Marks multiple time entries as paid using a single UPDATE statement.
     * 
     * @param ids list of time entry IDs to mark as paid
     * @return number of updated rows
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE TimeEntry t SET t.paid = true WHERE t.id IN :ids")
    int updateMultipleToPaid(@Param("ids") List<Long> ids);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE TimeEntry t SET t.clockIn = :clockIn, t.clockOut = :clockOut, t.paid = :paid," +
            " t.companyPaymentInformation = :companyPaymentInformation WHERE t.id = :id")
    int updateWithId(@Param("id") Long id,
                     @Param("clockIn") LocalDateTime clockIn,
                     @Param("clockOut") LocalDateTime clockOut,
                     @Param("paid") boolean paid,
                     @Param("companyPaymentInformation") CompanyPaymentInformation companyPaymentInformation);

    /**
     * Deletes multiple time entries using a single DELETE statement.
     * 
     * @param ids list of time entry IDs to delete
     * @return number of deleted rows
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM TimeEntry t WHERE t.id IN :ids")
    int deleteByIds(@Param("ids") List<Long> ids);

    @Query("SELECT t FROM TimeEntry t WHERE t.user.id = :userId AND t.clockIn >= :start AND t.clockIn < :end ORDER BY t.clockIn ASC")
    List<TimeEntry> timeEntriesFromPeriod(@Param("userId") Long userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    /**
     * Retrieves time entries for a user in a specific month with cursor-based pagination.
     * Returns entries where id > cursor, ordered by clockIn and then by id for stable pagination.
     * 
     * @param userId User ID
     * @param start Start of month (LocalDateTime)
     * @param end End of month (LocalDateTime)
     * @param cursor Last ID from previous page (null for first page)
     * @param limit Number of items to fetch (limit + 1 to detect if there are more)
     * @return List of time entries
     */
    @Query("SELECT t FROM TimeEntry t WHERE t.user.id = :userId AND t.clockIn >= :start AND t.clockIn < :end " +
            "AND (:cursor IS NULL OR t.id > :cursor) ORDER BY t.clockIn ASC, t.id ASC")
    List<TimeEntry> timeEntriesFromMonthWithCursor(@Param("userId") Long userId, 
                                                    @Param("start") LocalDateTime start, 
                                                    @Param("end") LocalDateTime end,
                                                    @Param("cursor") Long cursor);

    /**
     * Retrieves all time entries for a user with cursor-based pagination.
     * Returns entries where id > cursor, ordered by clockIn and then by id for stable pagination.
     * 
     * @param userId User ID
     * @param cursor Last ID from previous page (null for first page)
     * @return List of time entries
     */
    @Query("SELECT t FROM TimeEntry t WHERE t.user.id = :userId " +
            "AND (:cursor IS NULL OR t.id > :cursor) ORDER BY t.clockIn ASC, t.id ASC")
    List<TimeEntry> allTimeEntriesWithCursor(@Param("userId") Long userId, 
                                             @Param("cursor") Long cursor);
}
