package com.sagar.repository;

import com.sagar.entity.SocialLink;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SocialLinkRepository implements ReactivePanacheMongoRepository<SocialLink> {
}

