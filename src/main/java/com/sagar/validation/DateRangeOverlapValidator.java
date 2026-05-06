package com.sagar.validation;

import com.sagar.exceptions.ApplicationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Generic, reusable CDI bean that validates a candidate DateRange
 * does not overlap with any existing DateRangeValidatable records,
 * excluding the record being updated (identified by its entity id).
 *
 * Usage:
 *   validator.validate(candidateRange, excludeId, existingList, "Experience");
 *
 * Designed following:
 *   - SRP  : only responsible for overlap detection
 *   - OCP  : open to any entity type via DateRangeValidatable interface
 *   - DIP  : consumers depend on this abstraction, not concrete logic
 */
@ApplicationScoped
public class DateRangeOverlapValidator {

    /**
     * @param candidate   The DateRange being created or updated.
     * @param excludeId   The string id of the entity being updated (null for creates).
     * @param existing    All persisted entities of the same type.
     * @param entityLabel Human-readable name used in error messages (e.g. "Experience").
     */
    public <T extends DateRangeValidatable> void validate(
            DateRange candidate,
            String excludeId,
            List<T> existing,
            String entityLabel
    ) {
        for (T record : existing) {
            // Skip the record itself during an update
            if (excludeId != null
                    && record.getEntityId() != null
                    && record.getEntityId().toHexString().equals(excludeId)) {
                continue;
            }

            if (candidate.overlaps(record.resolveDateRange())) {
                throw new ApplicationException(
                        entityLabel + " date range " + candidate
                                + " overlaps with an existing entry " + record.resolveDateRange(),
                        Response.Status.CONFLICT.getStatusCode()
                );
            }
        }
    }
}

