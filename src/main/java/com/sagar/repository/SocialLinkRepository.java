package com.sagar.repository;
import com.sagar.entity.SocialLink;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class SocialLinkRepository implements PanacheMongoRepository<SocialLink> {
}
