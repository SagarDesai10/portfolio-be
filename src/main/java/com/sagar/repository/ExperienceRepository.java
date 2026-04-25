package com.sagar.repository;

import com.sagar.entity.Experience;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class ExperienceRepository implements PanacheMongoRepository<Experience> {

    public List<Experience> getAll() {
        return listAll();
    }

    public Experience getById(String id) {
        return findById(new ObjectId(id));
    }

    public void update(String id, Experience updated) {
        Experience existing = findById(new ObjectId(id));
        if (existing != null) {
            updated.id = existing.id;
            update(updated);
        }
    }

    public boolean deleteById(String id) {
        return deleteById(new ObjectId(id));
    }
}

