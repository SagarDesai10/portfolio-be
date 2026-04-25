package com.sagar.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "about")
public class About {

    public ObjectId id;
    public String name;
    public String profession;
    public String email;
    public List<String> about;
    public String degree;
    public String role;
    public String location;
}

