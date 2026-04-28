package com.sagar.repository;
import com.sagar.entity.Certificate;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class CertificateRepository implements PanacheMongoRepository<Certificate> {
}
