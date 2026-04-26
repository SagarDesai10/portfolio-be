package com.sagar.repository;
import com.sagar.entity.Education;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class EducationRepository implements ReactivePanacheMongoRepository<Education> {
}
