package com.sagar.repository;
import com.sagar.entity.About;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class AboutRepository implements ReactivePanacheMongoRepository<About> {
}
