package com.sagar.entity;

import com.sagar.validation.DateRange;
import com.sagar.validation.DateRangeValidatable;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;

@MongoEntity(collection = "educations")
public class Education implements DateRangeValidatable {

    public ObjectId id;
    public String stream;
    public String clgName;
    public Integer startYear;
    public Integer endYear;
    public String marks;
    public List<String> about;

    @Override
    public ObjectId getEntityId() {
        return id;
    }

    /**
     * Treats startYear as Jan-YYYY and endYear as Dec-YYYY (or current month if null).
     */
    @Override
    public DateRange resolveDateRange() {
        YearMonth start = YearMonth.of(startYear, Month.JANUARY);
        YearMonth end = (endYear != null)
                ? YearMonth.of(endYear, Month.DECEMBER)
                : YearMonth.now();
        return new DateRange(start, end);
    }
}

