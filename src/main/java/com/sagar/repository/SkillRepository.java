package com.sagar.repository;
import com.sagar.entity.Skill;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class SkillRepository implements PanacheMongoRepository<Skill> {
}
