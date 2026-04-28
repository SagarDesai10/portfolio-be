package com.sagar.repository;
import com.sagar.entity.Project;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class ProjectRepository implements PanacheMongoRepository<Project> {
}
