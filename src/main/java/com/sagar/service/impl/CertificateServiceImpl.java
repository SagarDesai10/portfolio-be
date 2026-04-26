package com.sagar.service.impl;

import com.sagar.entity.Certificate;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.CertificateMapper;
import com.sagar.repository.CertificateRepository;
import com.sagar.service.CertificateService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.CertificateDTO;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class CertificateServiceImpl implements CertificateService {

    @Inject
    private CertificateRepository repository;

    @Inject
    private CertificateMapper mapper;

    @Override
    public Uni<String> createCertificate(CertificateDTO certificateDTO) {
        return repository.persist(mapper.toEntity(certificateDTO))
                .map(e -> AppConstants.CREATED_SUCCESSFULLY);
    }

    @Override
    public Uni<CertificateDTO> updateCertificate(String id, CertificateDTO certificateDTO) {
        return findCertificate(id)
                .flatMap(entity -> {
                    mapper.updateEntityFromDTO(certificateDTO, entity);
                    return repository.update(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Uni<String> deleteCertificate(String id) {
        return findCertificate(id)
                .flatMap(entity -> repository.deleteById(entity.id))
                .map(deleted -> AppConstants.DELETED_SUCCESSFULLY);
    }

    @Override
    public Uni<List<CertificateDTO>> getAllCertificates() {
        return repository.listAll()
                .map(mapper::toDTOList);
    }

    private Uni<Certificate> findCertificate(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            return Uni.createFrom().failure(
                    new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST));
        }
        return repository.findByIdOptional(new ObjectId(id))
                .map(opt -> opt.orElseThrow(() ->
                        new ApplicationException(AppConstants.CERTIFICATE_NOT_FOUND, AppConstants.STATUS_NOT_FOUND)));
    }
}
