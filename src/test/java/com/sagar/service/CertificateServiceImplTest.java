package com.sagar.service;

import com.sagar.entity.Certificate;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.CertificateMapper;
import com.sagar.repository.CertificateRepository;
import com.sagar.service.impl.CertificateServiceImpl;
import com.sagar.util.AppConstants;
import com.sagar.util.TestDataFactory;
import org.acme.beans.CertificateDTO;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock CertificateRepository repository;
    @Mock CertificateMapper     mapper;

    @InjectMocks CertificateServiceImpl service;

    private Certificate    entity;
    private CertificateDTO dto;
    private String         validId;

    @BeforeEach
    void setUp() {
        entity  = TestDataFactory.certificateEntity();
        dto     = TestDataFactory.certificateDTO();
        validId = entity.id.toHexString();
    }

    @Test
    void createCertificate_persistsAndReturnsSuccess() {
        when(mapper.toEntity(dto)).thenReturn(entity);

        String result = service.createCertificate(dto);

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
        verify(repository).persist(entity);
    }

    @Test
    void getAllCertificates_returnsMappedList() {
        when(repository.listAll()).thenReturn(List.of(entity));
        when(mapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<CertificateDTO> result = service.getAllCertificates();

        assertThat(result).hasSize(1).contains(dto);
    }

    @Test
    void updateCertificate_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        CertificateDTO result = service.updateCertificate(validId, dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntityFromDTO(dto, entity);
        verify(repository).update(entity);
    }

    @Test
    void updateCertificate_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateCertificate(TestDataFactory.INVALID_ID, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateCertificate_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateCertificate(validId, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void deleteCertificate_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));

        String result = service.deleteCertificate(validId);

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
        verify(repository).deleteById(entity.id);
    }

    @Test
    void deleteCertificate_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteCertificate(validId))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }
}

