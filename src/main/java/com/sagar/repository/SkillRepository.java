package com.sagar.repository;

import com.sagar.entity.Skill;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class SkillRepository implements PanacheMongoRepository<Skill> {

    public List<Skill> getAll() {
        return listAll();
    }

    public Skill getById(String id) {
        return findById(new ObjectId(id));
    }

    public void update(String id, Skill updated) {
        Skill existing = findById(new ObjectId(id));
        if (existing != null) {
            updated.id = existing.id;
            update(updated);
        }
    }

    public boolean deleteById(String id) {
        return deleteById(new ObjectId(id));
    }
}

