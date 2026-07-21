package dev.alexis.hourlypaytracker.repository;

import dev.alexis.hourlypaytracker.entity.TimeEntry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeEntryRepositoryImpl implements TimeEntryRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<TimeEntry> findWithCursor(
            Long userId,
            Instant lastClockIn,
            Long lastId,
            Boolean paid,
            int pageSize,
            Sort.Direction direction
    ) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TimeEntry> query = cb.createQuery(TimeEntry.class);

        Root<TimeEntry> root = query.from(TimeEntry.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("user").get("id"), userId));

        if (paid != null) {
            predicates.add(cb.equal(root.get("paid"), paid));
        }

        if (lastClockIn != null && lastId != null) {

            Predicate cursorPredicate;

            if (direction == Sort.Direction.ASC) {

                cursorPredicate = cb.or(
                        cb.greaterThan(root.get("clockIn"), lastClockIn),
                        cb.and(
                                cb.equal(root.get("clockIn"), lastClockIn),
                                cb.greaterThan(root.get("id"), lastId)
                        )
                );

            } else {

                cursorPredicate = cb.or(
                        cb.lessThan(root.get("clockIn"), lastClockIn),
                        cb.and(
                                cb.equal(root.get("clockIn"), lastClockIn),
                                cb.lessThan(root.get("id"), lastId)
                        )
                );

            }

            predicates.add(cursorPredicate);
        }

        query.where(predicates.toArray(Predicate[]::new));

        if (direction == Sort.Direction.ASC) {

            query.orderBy(
                    cb.asc(root.get("clockIn")),
                    cb.asc(root.get("id"))
            );

        } else {

            query.orderBy(
                    cb.desc(root.get("clockIn")),
                    cb.desc(root.get("id"))
            );

        }

        return em.createQuery(query)
                .setMaxResults(pageSize + 1)
                .getResultList();
    }
}
