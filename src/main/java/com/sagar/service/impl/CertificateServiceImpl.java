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
import java.util.Optional;

@ApplicationScoped
public class CertificateServiceImpl implements CertificateService {

    @Inject
    private CertificateRepository repository;

    @Inject
    private CertificateMapper mapper;

    @Override
    public String createCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = mapper.toEntity(certificateDTO);
        repository.persist(certificate);
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public CertificateDTO updateCertificate(String id, CertificateDTO certificateDTO) {
        Certificate certificate = findCertificate(id);
        mapper.updateEntityFromDTO(certificateDTO, certificate);
        repository.update(certificate);
        return mapper.toDTO(certificate);
    }

    @Override
    public String deleteCertificate(String id) {
        Certificate certificate = findCertificate(id);
        repository.deleteById(certificate.id);
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
        Optional<Certificate> optCertificate = repository.findByIdOptional(new ObjectId(id));
        if (optCertificate.isEmpty()) {
            throw new ApplicationException(AppConstants.CERTIFICATE_NOT_FOUND, AppConstants.STATUS_NOT_FOUND);
        }
        return optCertificate.get();
    }
}
