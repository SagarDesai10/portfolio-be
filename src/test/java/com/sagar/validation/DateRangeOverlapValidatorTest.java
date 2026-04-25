package com.sagar.validation;

import com.sagar.entity.Experience;
import com.sagar.exceptions.ApplicationException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link DateRangeOverlapValidator}.
 */
class DateRangeOverlapValidatorTest {

    private DateRangeOverlapValidator validator;

    private static final ObjectId ID_1 = new ObjectId();
    private static final ObjectId ID_2 = new ObjectId();

    @BeforeEach
    void setUp() {
        validator = new DateRangeOverlapValidator();
    }

    private Experience makeExperience(ObjectId id, String start, String end) {
        Experience e = new Experience();
        e.id        = id;
        e.startDate = start;
        e.endDate   = end;
        return e;
    }

    @Test
    void validate_noExistingRecords_doesNotThrow() {
        DateRange candidate = new DateRange(
                DateRangeParser.parseRequired("Jan-2023", "start"),
                DateRangeParser.parseEndDate("Jun-2023")
        );
        assertThatCode(() -> validator.validate(candidate, null, List.of(), "Experience"))
                .doesNotThrowAnyException();
    }

    @Test
    void validate_nonOverlappingRanges_doesNotThrow() {
        Experience existing = makeExperience(ID_1, "Jan-2022", "Dec-2022");
        DateRange candidate = new DateRange(
                DateRangeParser.parseRequired("Jan-2023", "start"),
                DateRangeParser.parseEndDate("Dec-2023")
        );
        assertThatCode(() -> validator.validate(candidate, null, List.of(existing), "Experience"))
                .doesNotThrowAnyException();
    }

    @Test
    void validate_overlappingRanges_throwsConflict() {
        Experience existing = makeExperience(ID_1, "Jan-2023", "Jun-2023");
        DateRange candidate = new DateRange(
                DateRangeParser.parseRequired("Apr-2023", "start"),
                DateRangeParser.parseEndDate("Sep-2023")
        );
        assertThatThrownBy(() -> validator.validate(candidate, null, List.of(existing), "Experience"))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("overlaps");
    }

    @Test
    void validate_updateSelf_doesNotThrow() {
        // When updating, we exclude the record with the same id
        Experience existing = makeExperience(ID_1, "Jan-2023", "Jun-2023");
        DateRange candidate = new DateRange(
                DateRangeParser.parseRequired("Jan-2023", "start"),
                DateRangeParser.parseEndDate("Jun-2023")
        );
        assertThatCode(() -> validator.validate(candidate, ID_1.toHexString(), List.of(existing), "Experience"))
                .doesNotThrowAnyException();
    }

    @Test
    void validate_updateOverlapsOtherRecord_throwsConflict() {
        Experience existing1 = makeExperience(ID_1, "Jan-2023", "Jun-2023");
        Experience existing2 = makeExperience(ID_2, "Jul-2023", "Dec-2023");
        DateRange candidate = new DateRange(
                DateRangeParser.parseRequired("May-2023", "start"),
                DateRangeParser.parseEndDate("Aug-2023")
        );
        // Updating existing1 — but still overlaps with existing2
        assertThatThrownBy(() -> validator.validate(candidate, ID_1.toHexString(), List.of(existing1, existing2), "Experience"))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("overlaps");
    }
}

