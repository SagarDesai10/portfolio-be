package com.sagar.repository;

import com.sagar.entity.Certificate;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class CertificateRepository implements PanacheMongoRepository<Certificate> {

    public List<Certificate> getAll() {
        return listAll();
    }

    public Certificate getById(String id) {
        return findById(new ObjectId(id));
    }

    public void update(String id, Certificate updated) {
        Certificate existing = findById(new ObjectId(id));
        if (existing != null) {
            updated.id = existing.id;
            update(updated);
        }
    }

    public boolean deleteById(String id) {
        return deleteById(new ObjectId(id));
    }
}

