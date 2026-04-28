package com.sagar.repository;
import com.sagar.entity.Education;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class EducationRepository implements PanacheMongoRepository<Education> {
}
