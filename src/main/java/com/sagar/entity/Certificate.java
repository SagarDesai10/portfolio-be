package com.sagar.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(collection = "certificates")
public class Certificate {

    public ObjectId id;
    public String name;
    public String skill;
    public String link;
    public Integer year;
}

