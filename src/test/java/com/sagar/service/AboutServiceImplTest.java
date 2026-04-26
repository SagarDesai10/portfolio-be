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
import io.quarkus.mongodb.panache.reactive.ReactivePanacheQuery;
import io.smallrye.mutiny.Uni;
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
    @Mock ReactivePanacheQuery<About> query;

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
        when(repository.count()).thenReturn(Uni.createFrom().item(0L));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.persist(entity)).thenReturn(Uni.createFrom().item(entity));

        String result = service.createAbout(dto).await().indefinitely();

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
    }

    @Test
    void createAbout_whenRecordAlreadyExists_throwsConflict() {
        when(repository.count()).thenReturn(Uni.createFrom().item(1L));

        assertThatThrownBy(() -> service.createAbout(dto).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_CONFLICT);
    }

    // ── getAbout ───────────────────────────────────────────────────────────────

    @Test
    void getAbout_whenRecordExists_returnsDTOWithExperience() {
        when(repository.findAll()).thenReturn(query);
        when(query.firstResult()).thenReturn(Uni.createFrom().item(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        when(experienceRepository.listAll()).thenReturn(Uni.createFrom().item(List.of()));

        AboutDTO result = service.getAbout().await().indefinitely();

        assertThat(result).isNotNull();
    }

    @Test
    void getAbout_whenNoRecord_throwsNotFound() {
        when(repository.findAll()).thenReturn(query);
        when(query.firstResult()).thenReturn(Uni.createFrom().item(() -> (About) null));

        assertThatThrownBy(() -> service.getAbout().await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    // ── updateAbout ────────────────────────────────────────────────────────────

    @Test
    void updateAbout_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.of(entity)));
        when(repository.update(entity)).thenReturn(Uni.createFrom().item(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        when(experienceRepository.listAll()).thenReturn(Uni.createFrom().item(List.of()));

        AboutDTO result = service.updateAbout(validId, dto).await().indefinitely();

        assertThat(result).isNotNull();
        verify(mapper).updateEntityFromDTO(dto, entity);
    }

    @Test
    void updateAbout_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateAbout(TestDataFactory.INVALID_ID, dto).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateAbout_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.empty()));

        assertThatThrownBy(() -> service.updateAbout(validId, dto).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    // ── deleteAbout ────────────────────────────────────────────────────────────

    @Test
    void deleteAbout_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.of(entity)));
        when(repository.deleteById(entity.id)).thenReturn(Uni.createFrom().item(true));

        String result = service.deleteAbout(validId).await().indefinitely();

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
    }

    @Test
    void deleteAbout_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.empty()));

        assertThatThrownBy(() -> service.deleteAbout(validId).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    // ── experience calculation ─────────────────────────────────────────────────

    @Test
    void getAbout_calculatesExperienceFromExperienceRecords() {
        Experience exp = new Experience();
        exp.startDate = "Jan-2023";
        exp.endDate   = "Jan-2024";

        when(repository.findAll()).thenReturn(query);
        when(query.firstResult()).thenReturn(Uni.createFrom().item(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        when(experienceRepository.listAll()).thenReturn(Uni.createFrom().item(List.of(exp)));

        AboutDTO result = service.getAbout().await().indefinitely();

        assertThat(result.getExperience()).isEqualTo(1.0);
    }
}
