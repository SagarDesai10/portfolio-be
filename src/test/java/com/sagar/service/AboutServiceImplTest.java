package com.sagar.service;

import com.sagar.entity.About;
import com.sagar.entity.Experience;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.AboutMapper;
import com.sagar.repository.AboutRepository;
import com.sagar.repository.ExperienceRepository;
import com.sagar.service.impl.AboutServiceImpl;
import com.sagar.util.AppConstants;
import com.sagar.util.TestDataFactory;
import org.acme.beans.AboutDTO;
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

/**
 * Unit tests for {@link AboutServiceImpl}.
 * All dependencies are mocked with Mockito — no Quarkus container needed.
 */
@ExtendWith(MockitoExtension.class)
class AboutServiceImplTest {

    @Mock AboutRepository        repository;
    @Mock ExperienceRepository   experienceRepository;
    @Mock AboutMapper            mapper;

    @InjectMocks AboutServiceImpl service;

    private About    entity;
    private AboutDTO dto;
    private String   validId;

    @BeforeEach
    void setUp() {
        entity  = TestDataFactory.aboutEntity();
        dto     = TestDataFactory.aboutDTO();
        validId = entity.id.toHexString();
    }

    // ── createAbout ────────────────────────────────────────────────────────────

    @Test
    void createAbout_whenNoExistingRecord_persistsAndReturnsSuccess() {
        when(repository.count()).thenReturn(0L);
        when(mapper.toEntity(dto)).thenReturn(entity);

        String result = service.createAbout(dto);

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
        verify(repository).persist(entity);
    }

    @Test
    void createAbout_whenRecordAlreadyExists_throwsConflict() {
        when(repository.count()).thenReturn(1L);

        assertThatThrownBy(() -> service.createAbout(dto))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("already exists")
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_CONFLICT);
    }

    // ── getAbout ───────────────────────────────────────────────────────────────

    @Test
    void getAbout_whenRecordExists_returnsDTOWithExperience() {
        when(repository.getAll()).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        when(experienceRepository.listAll()).thenReturn(List.of());

        AboutDTO result = service.getAbout();

        assertThat(result).isNotNull();
        verify(mapper).toDTO(entity);
    }

    @Test
    void getAbout_whenNoRecord_throwsNotFound() {
        when(repository.getAll()).thenReturn(null);

        assertThatThrownBy(() -> service.getAbout())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    // ── updateAbout ────────────────────────────────────────────────────────────

    @Test
    void updateAbout_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        when(experienceRepository.listAll()).thenReturn(List.of());

        AboutDTO result = service.updateAbout(validId, dto);

        assertThat(result).isNotNull();
        verify(mapper).updateEntityFromDTO(dto, entity);
        verify(repository).update(entity);
    }

    @Test
    void updateAbout_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateAbout(TestDataFactory.INVALID_ID, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateAbout_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateAbout(validId, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    // ── deleteAbout ────────────────────────────────────────────────────────────

    @Test
    void deleteAbout_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));

        String result = service.deleteAbout(validId);

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
        verify(repository).deleteById(entity.id);
    }

    @Test
    void deleteAbout_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteAbout(validId))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    // ── experience calculation ─────────────────────────────────────────────────

    @Test
    void getAbout_calculatesExperienceFromExperienceRecords() {
        Experience exp = new Experience();
        exp.startDate = "Jan-2023";
        exp.endDate   = "Jan-2024";  // 12 months → 1.0 year

        when(repository.getAll()).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        when(experienceRepository.listAll()).thenReturn(List.of(exp));

        AboutDTO result = service.getAbout();

        assertThat(result.getExperience()).isEqualTo(1.0);
    }
}

