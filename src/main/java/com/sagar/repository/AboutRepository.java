package com.sagar.repository;

import com.sagar.entity.About;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class AboutRepository implements PanacheMongoRepository<About> {

    public About getAll() {
        return findAll().firstResult();
    }

    public About findById(String id) {
        return findById(new ObjectId(id));
    }

    public void update(String id, About updated) {
        About existing = findById(new ObjectId(id));
        if (existing != null) {
            updated.id = existing.id;
            update(updated);
        }
    }

    public boolean deleteById(String id) {
        return deleteById(new ObjectId(id));
    }
}

