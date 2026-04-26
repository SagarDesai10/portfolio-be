package com.sagar.service;

import com.sagar.entity.Education;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.EducationMapper;
import com.sagar.repository.EducationRepository;
import com.sagar.service.impl.EducationServiceImpl;
import com.sagar.util.AppConstants;
import com.sagar.util.TestDataFactory;
import com.sagar.validation.DateRangeOverlapValidator;
import io.smallrye.mutiny.Uni;
import org.acme.beans.EducationDTO;
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
class EducationServiceImplTest {

    @Mock EducationRepository        repository;
    @Mock EducationMapper            mapper;
    @Mock DateRangeOverlapValidator  overlapValidator;

    @InjectMocks EducationServiceImpl service;

    private Education    entity;
    private EducationDTO dto;
    private String       validId;

    @BeforeEach
    void setUp() {
        entity  = TestDataFactory.educationEntity();
        dto     = TestDataFactory.educationDTO();
        validId = entity.id.toHexString();
    }

    @Test
    void createEducation_validDTO_persistsAndReturnsSuccess() {
        when(repository.listAll()).thenReturn(Uni.createFrom().item(List.of()));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.persist(entity)).thenReturn(Uni.createFrom().item(entity));

        String result = service.createEducation(dto).await().indefinitely();

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
        verify(overlapValidator).validate(any(), isNull(), any(), eq("Education"));
    }

    @Test
    void createEducation_nullStartYear_throwsBadRequest() {
        EducationDTO bad = new EducationDTO();
        bad.setClgName("MIT");
        bad.setStream("CS");

        assertThatThrownBy(() -> service.createEducation(bad).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void createEducation_startAfterEnd_throwsIllegalArgument() {
        EducationDTO bad = new EducationDTO();
        bad.setStartYear(2024);
        bad.setEndYear(2020);  // end before start

        assertThatThrownBy(() -> service.createEducation(bad))
                .isInstanceOf(Exception.class);
    }

    @Test
    void getAllEducations_returnsMappedList() {
        when(repository.listAll()).thenReturn(Uni.createFrom().item(List.of(entity)));
        when(mapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<EducationDTO> result = service.getAllEducations().await().indefinitely();

        assertThat(result).hasSize(1).contains(dto);
    }

    @Test
    void updateEducation_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.of(entity)));
        when(repository.listAll()).thenReturn(Uni.createFrom().item(List.of()));
        when(repository.update(entity)).thenReturn(Uni.createFrom().item(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        EducationDTO result = service.updateEducation(validId, dto).await().indefinitely();

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntityFromDTO(dto, entity);
    }

    @Test
    void updateEducation_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateEducation(TestDataFactory.INVALID_ID, dto).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateEducation_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.empty()));

        assertThatThrownBy(() -> service.updateEducation(validId, dto).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void deleteEducation_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.of(entity)));
        when(repository.deleteById(entity.id)).thenReturn(Uni.createFrom().item(true));

        String result = service.deleteEducation(validId).await().indefinitely();

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
    }

    @Test
    void deleteEducation_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.empty()));

        assertThatThrownBy(() -> service.deleteEducation(validId).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void createEducation_nullEndYear_usesCurrentYearMonth() {
        EducationDTO noEnd = new EducationDTO();
        noEnd.setStartYear(2018);

        when(repository.listAll()).thenReturn(Uni.createFrom().item(List.of()));
        when(mapper.toEntity(noEnd)).thenReturn(entity);
        when(repository.persist(entity)).thenReturn(Uni.createFrom().item(entity));

        assertThatCode(() -> service.createEducation(noEnd).await().indefinitely())
                .doesNotThrowAnyException();
    }
}
