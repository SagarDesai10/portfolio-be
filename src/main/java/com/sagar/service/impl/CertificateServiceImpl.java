package com.sagar.service.impl;

import com.sagar.entity.Certificate;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.CertificateMapper;
import com.sagar.repository.CertificateRepository;
import com.sagar.service.CertificateService;
import com.sagar.util.AppConstants;
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
    public String createCertificate(CertificateDTO certificateDTO) {
        repository.persist(mapper.toEntity(certificateDTO));
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public CertificateDTO updateCertificate(String id, CertificateDTO certificateDTO) {
        Certificate entity = findCertificate(id);
        mapper.updateEntityFromDTO(certificateDTO, entity);
        repository.update(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public String deleteCertificate(String id) {
        Certificate entity = findCertificate(id);
        repository.deleteById(entity.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public List<CertificateDTO> getAllCertificates() {
        return mapper.toDTOList(repository.listAll());
    }

    private Certificate findCertificate(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        return repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ApplicationException(AppConstants.CERTIFICATE_NOT_FOUND, AppConstants.STATUS_NOT_FOUND));
    }
}
