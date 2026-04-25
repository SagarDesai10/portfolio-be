package com.sagar.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Named;

public interface BaseMapper {

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return (id != null && !id.isEmpty()) ? new ObjectId(id) : null;
    }

    @Named("objectIdToString")
    default String objectIdToString(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }

    default String map(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value;
    }
}

