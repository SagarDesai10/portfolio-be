package com.sagar.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "projects")
public class Project {

    public ObjectId id;
    public String category;
    public String name;
    public String description;
    public List<String> keyTech;
    public String githubLink;
    public String liveLink;
}

