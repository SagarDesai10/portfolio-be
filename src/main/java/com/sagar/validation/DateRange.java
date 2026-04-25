package com.sagar.validation;

import java.time.YearMonth;

/**
 * Immutable value object representing a closed date range [start, end].
 * Both start and end are inclusive YearMonth values.
 */
public final class DateRange {

    private final YearMonth start;
    private final YearMonth end;

    public DateRange(YearMonth start, YearMonth end) {
        if (start == null) throw new IllegalArgumentException("start must not be null");
        if (end == null) throw new IllegalArgumentException("end must not be null");
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("start must not be after end");
        }
        this.start = start;
        this.end = end;
    }

    public YearMonth getStart() { return start; }
    public YearMonth getEnd()   { return end;   }

    /**
     * Returns true if this range overlaps with the other range.
     * Two ranges overlap when one starts before the other ends (inclusive).
     */
    public boolean overlaps(DateRange other) {
        return !this.start.isAfter(other.end) && !other.start.isAfter(this.end);
    }

    @Override
    public String toString() {
        return start + " to " + end;
    }
}

