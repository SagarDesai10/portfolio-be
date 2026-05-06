package com.sagar.entity;

import com.sagar.validation.DateRange;
import com.sagar.validation.DateRangeParser;
import com.sagar.validation.DateRangeValidatable;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "experiences")
public class Experience implements DateRangeValidatable {

    public ObjectId id;
    public String companyName;
    public String position;
    public String startDate;
    public String endDate;
    public List<String> about;

    @Override
    public ObjectId getEntityId() {
        return id;
    }

    @Override
    public DateRange resolveDateRange() {
        return new DateRange(
                DateRangeParser.parseRequired(startDate, "startDate"),
                DateRangeParser.parseEndDate(endDate)
        );
    }
}

