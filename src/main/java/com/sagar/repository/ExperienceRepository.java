package com.sagar.repository;
import com.sagar.entity.Experience;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class ExperienceRepository implements PanacheMongoRepository<Experience> {
}
