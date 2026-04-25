package com.sagar.validation;

import com.sagar.exceptions.ApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Utility class responsible for parsing date strings into YearMonth.
 *
 * Accepted formats:
 *   - "MMM-yyyy"  (e.g. "Jun-2024", case-insensitive)
 *   - "present"   (case-insensitive) → resolved to YearMonth.now()
 *   - null / blank → treated as "present" when used as endDate
 */
public final class DateRangeParser {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMM-yyyy", Locale.ENGLISH);

    private DateRangeParser() {}

    /**
     * Parses a required date field. Throws 400 if blank or invalid.
     */
    public static YearMonth parseRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ApplicationException(
                    fieldName + " is required",
                    Response.Status.BAD_REQUEST.getStatusCode()
            );
        }
        return parse(value, fieldName);
    }

    /**
     * Parses an optional end-date field.
     * Returns YearMonth.now() if the value is null, blank, or "present".
     */
    public static YearMonth parseEndDate(String value) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase("present")) {
            return YearMonth.now();
        }
        return parse(value, "endDate");
    }

    private static YearMonth parse(String value, String fieldName) {
        try {
            return YearMonth.parse(normalizeMonth(value), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ApplicationException(
                    fieldName + " must be in MMM-yyyy format (e.g. Jun-2024)",
                    Response.Status.BAD_REQUEST.getStatusCode()
            );
        }
    }

    /**
     * Normalises "jun-2024" → "Jun-2024" so the English locale formatter works
     * regardless of the casing the caller provides.
     */
    private static String normalizeMonth(String value) {
        if (value == null || !value.contains("-")) return value;
        String[] parts = value.split("-", 2);
        String month = parts[0].substring(0, 1).toUpperCase(Locale.ENGLISH)
                + parts[0].substring(1).toLowerCase(Locale.ENGLISH);
        return month + "-" + parts[1];
    }
}

