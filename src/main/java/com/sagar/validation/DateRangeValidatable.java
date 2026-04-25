package com.sagar.validation;

import org.bson.types.ObjectId;

/**
 * Contract that any entity with a date range must implement
 * so the generic overlap validator can work with it.
 */
public interface DateRangeValidatable {

    /** The entity's own DB id (null for new records). */
    ObjectId getEntityId();

    /** The resolved date range for this entity. */
    DateRange getDateRange();
}

