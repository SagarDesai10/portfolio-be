package com.sagar.repository;

import com.sagar.entity.Project;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class ProjectRepository implements PanacheMongoRepository<Project> {

    public List<Project> getAll() {
        return listAll();
    }

    public Project getById(String id) {
        return findById(new ObjectId(id));
    }

    public void update(String id, Project updated) {
        Project existing = findById(new ObjectId(id));
        if (existing != null) {
            updated.id = existing.id;
            update(updated);
        }
    }

    public boolean deleteById(String id) {
        return deleteById(new ObjectId(id));
    }
}

