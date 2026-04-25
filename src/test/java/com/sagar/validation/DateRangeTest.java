package com.sagar.validation;

import com.sagar.exceptions.ApplicationException;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link DateRange} value object.
 */
class DateRangeTest {

    private static final YearMonth JAN_2023 = YearMonth.of(2023, 1);
    private static final YearMonth JUN_2023 = YearMonth.of(2023, 6);
    private static final YearMonth DEC_2023 = YearMonth.of(2023, 12);
    private static final YearMonth JAN_2024 = YearMonth.of(2024, 1);
    private static final YearMonth DEC_2024 = YearMonth.of(2024, 12);

    @Test
    void constructor_validRange_succeeds() {
        DateRange range = new DateRange(JAN_2023, DEC_2023);
        assertThat(range.getStart()).isEqualTo(JAN_2023);
        assertThat(range.getEnd()).isEqualTo(DEC_2023);
    }

    @Test
    void constructor_sameStartAndEnd_succeeds() {
        DateRange range = new DateRange(JAN_2023, JAN_2023);
        assertThat(range.getStart()).isEqualTo(range.getEnd());
    }

    @Test
    void constructor_nullStart_throwsIllegalArgument() {
        assertThatThrownBy(() -> new DateRange(null, DEC_2023))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("start must not be null");
    }

    @Test
    void constructor_nullEnd_throwsIllegalArgument() {
        assertThatThrownBy(() -> new DateRange(JAN_2023, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("end must not be null");
    }

    @Test
    void constructor_startAfterEnd_throwsIllegalArgument() {
        assertThatThrownBy(() -> new DateRange(DEC_2023, JAN_2023))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("start must not be after end");
    }

    @Test
    void overlaps_overlappingRanges_returnsTrue() {
        DateRange r1 = new DateRange(JAN_2023, JUN_2023);   // Jan–Jun 2023
        DateRange r2 = new DateRange(JUN_2023, DEC_2023);   // Jun–Dec 2023 (shares Jun)
        assertThat(r1.overlaps(r2)).isTrue();
        assertThat(r2.overlaps(r1)).isTrue();
    }

    @Test
    void overlaps_containedRange_returnsTrue() {
        DateRange outer = new DateRange(JAN_2023, DEC_2023);
        DateRange inner = new DateRange(JAN_2023, JUN_2023);
        assertThat(outer.overlaps(inner)).isTrue();
    }

    @Test
    void overlaps_nonOverlappingRanges_returnsFalse() {
        DateRange r1 = new DateRange(JAN_2023, JUN_2023);   // Jan–Jun 2023
        DateRange r2 = new DateRange(JAN_2024, DEC_2024);   // Jan–Dec 2024
        assertThat(r1.overlaps(r2)).isFalse();
        assertThat(r2.overlaps(r1)).isFalse();
    }

    @Test
    void toString_returnsExpectedFormat() {
        DateRange range = new DateRange(JAN_2023, DEC_2023);
        assertThat(range.toString()).contains("2023-01").contains("2023-12");
    }
}

