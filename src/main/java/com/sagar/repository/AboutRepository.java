package com.sagar.repository;
import com.sagar.entity.About;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class AboutRepository implements PanacheMongoRepository<About> {
}
