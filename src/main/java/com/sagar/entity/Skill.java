package com.sagar.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(collection = "skills")
public class Skill {

    public ObjectId id;
    public String category;
    public String name;
    public String img;
    public Integer stars;
}

