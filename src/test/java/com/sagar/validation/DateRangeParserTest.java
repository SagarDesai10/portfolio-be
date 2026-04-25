package com.sagar.validation;

import com.sagar.exceptions.ApplicationException;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DateRangeParser}.
 */
class DateRangeParserTest {

    // ── parseRequired ──────────────────────────────────────────────────────────

    @Test
    void parseRequired_validDate_returnsParsedYearMonth() {
        YearMonth result = DateRangeParser.parseRequired("Jun-2024", "startDate");
        assertEquals(YearMonth.of(2024, 6), result);
    }

    @Test
    void parseRequired_caseInsensitive_parsesCorrectly() {
        YearMonth result = DateRangeParser.parseRequired("jun-2024", "startDate");
        assertEquals(YearMonth.of(2024, 6), result);
    }

    @Test
    void parseRequired_nullValue_throwsBadRequest() {
        assertThatThrownBy(() -> DateRangeParser.parseRequired(null, "startDate"))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("startDate is required");
    }

    @Test
    void parseRequired_blankValue_throwsBadRequest() {
        assertThatThrownBy(() -> DateRangeParser.parseRequired("  ", "startDate"))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("startDate is required");
    }

    @Test
    void parseRequired_invalidFormat_throwsBadRequest() {
        assertThatThrownBy(() -> DateRangeParser.parseRequired("2024-06", "startDate"))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("MMM-yyyy");
    }

    @Test
    void parseRequired_invalidMonth_throwsBadRequest() {
        assertThatThrownBy(() -> DateRangeParser.parseRequired("Xyz-2024", "startDate"))
                .isInstanceOf(ApplicationException.class);
    }

    // ── parseEndDate ───────────────────────────────────────────────────────────

    @Test
    void parseEndDate_nullValue_returnsCurrentYearMonth() {
        assertEquals(YearMonth.now(), DateRangeParser.parseEndDate(null));
    }

    @Test
    void parseEndDate_blankValue_returnsCurrentYearMonth() {
        assertEquals(YearMonth.now(), DateRangeParser.parseEndDate("   "));
    }

    @Test
    void parseEndDate_presentLowercase_returnsCurrentYearMonth() {
        assertEquals(YearMonth.now(), DateRangeParser.parseEndDate("present"));
    }

    @Test
    void parseEndDate_presentUppercase_returnsCurrentYearMonth() {
        assertEquals(YearMonth.now(), DateRangeParser.parseEndDate("PRESENT"));
    }

    @Test
    void parseEndDate_validDate_returnsParsedYearMonth() {
        assertEquals(YearMonth.of(2025, 12), DateRangeParser.parseEndDate("Dec-2025"));
    }

    @Test
    void parseEndDate_invalidFormat_throwsBadRequest() {
        assertThatThrownBy(() -> DateRangeParser.parseEndDate("13-2024"))
                .isInstanceOf(ApplicationException.class);
    }
}
