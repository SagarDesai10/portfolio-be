package com.sagar.repository;
import com.sagar.entity.Experience;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class ExperienceRepository implements ReactivePanacheMongoRepository<Experience> {
}
