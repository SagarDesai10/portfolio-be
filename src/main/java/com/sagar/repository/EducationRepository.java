package com.sagar.repository;

import com.sagar.entity.Education;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class EducationRepository implements PanacheMongoRepository<Education> {

    public List<Education> getAll() {
        return listAll();
    }

    public Education getById(String id) {
        return findById(new ObjectId(id));
    }

    public void update(String id, Education updated) {
        Education existing = findById(new ObjectId(id));
        if (existing != null) {
            updated.id = existing.id;
            update(updated);
        }
    }

    public boolean deleteById(String id) {
        return deleteById(new ObjectId(id));
    }
}

