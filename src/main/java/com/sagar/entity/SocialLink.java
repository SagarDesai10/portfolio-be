package com.sagar.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(collection = "social_links")
public class SocialLink {

    public ObjectId id;
    public String name;
    public String link;
    public String img;
}

