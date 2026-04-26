package com.sagar.repository;
import com.sagar.entity.Skill;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class SkillRepository implements ReactivePanacheMongoRepository<Skill> {
}
