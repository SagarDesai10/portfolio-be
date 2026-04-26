package com.sagar.repository;
import com.sagar.entity.Certificate;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class CertificateRepository implements ReactivePanacheMongoRepository<Certificate> {
}
