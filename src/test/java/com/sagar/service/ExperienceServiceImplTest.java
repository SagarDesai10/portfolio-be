package com.sagar.service;

import com.sagar.entity.Experience;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.ExperienceMapper;
import com.sagar.repository.ExperienceRepository;
import com.sagar.service.impl.ExperiencServiceImpl;
import com.sagar.util.AppConstants;
import com.sagar.util.TestDataFactory;
import com.sagar.validation.DateRangeOverlapValidator;
import org.acme.beans.ExperienceDTO;
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
class ExperienceServiceImplTest {

    @Mock ExperienceRepository       repository;
    @Mock ExperienceMapper           mapper;
    @Mock DateRangeOverlapValidator  overlapValidator;

    @InjectMocks ExperiencServiceImpl service;

    private Experience    entity;
    private ExperienceDTO dto;
    private String        validId;

    @BeforeEach
    void setUp() {
        entity  = TestDataFactory.experienceEntity();
        dto     = TestDataFactory.experienceDTO();
        validId = entity.id.toHexString();
    }

    @Test
    void createExperience_validDTO_persistsAndReturnsSuccess() {
        when(repository.listAll()).thenReturn(List.of());
        when(mapper.toEntity(dto)).thenReturn(entity);

        String result = service.createExperience(dto);

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
        verify(repository).persist(entity);
        verify(overlapValidator).validate(any(), isNull(), any(), eq("Experience"));
    }

    @Test
    void createExperience_invalidStartDate_throwsBadRequest() {
        ExperienceDTO bad = new ExperienceDTO();
        bad.setStartDate("not-a-date");
        bad.setEndDate("Jun-2023");

        assertThatThrownBy(() -> service.createExperience(bad))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void createExperience_startAfterEnd_throwsIllegalArgument() {
        ExperienceDTO bad = new ExperienceDTO();
        bad.setStartDate("Dec-2023");
        bad.setEndDate("Jan-2023");

        assertThatThrownBy(() -> service.createExperience(bad))
                .isInstanceOf(Exception.class);
    }

    @Test
    void getAllExperiences_returnsMappedList() {
        when(repository.listAll()).thenReturn(List.of(entity));
        when(mapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<ExperienceDTO> result = service.getAllExperiences();

        assertThat(result).hasSize(1).contains(dto);
    }

    @Test
    void updateExperience_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));
        when(repository.listAll()).thenReturn(List.of());
        when(mapper.toDTO(entity)).thenReturn(dto);

        ExperienceDTO result = service.updateExperience(validId, dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntityFromDTO(dto, entity);
        verify(repository).update(entity);
    }

    @Test
    void updateExperience_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateExperience(TestDataFactory.INVALID_ID, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateExperience_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateExperience(validId, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void deleteExperience_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));

        String result = service.deleteExperience(validId);

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
        verify(repository).deleteById(entity.id);
    }

    @Test
    void deleteExperience_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteExperience(validId))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void createExperience_endDatePresent_isAccepted() {
        ExperienceDTO presentDto = new ExperienceDTO();
        presentDto.setStartDate("Jan-2023");
        presentDto.setEndDate("present");

        when(repository.listAll()).thenReturn(List.of());
        when(mapper.toEntity(presentDto)).thenReturn(entity);

        assertThatCode(() -> service.createExperience(presentDto))
                .doesNotThrowAnyException();
    }
}

