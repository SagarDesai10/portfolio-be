package com.sagar.service;

import com.sagar.entity.Project;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.ProjectMapper;
import com.sagar.repository.ProjectRepository;
import com.sagar.service.impl.ProjectServiceImpl;
import com.sagar.util.AppConstants;
import com.sagar.util.TestDataFactory;
import org.acme.beans.ProjectDTO;
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
class ProjectServiceImplTest {

    @Mock ProjectRepository repository;
    @Mock ProjectMapper     mapper;

    @InjectMocks ProjectServiceImpl service;

    private Project    entity;
    private ProjectDTO dto;
    private String     validId;

    @BeforeEach
    void setUp() {
        entity  = TestDataFactory.projectEntity();
        dto     = TestDataFactory.projectDTO();
        validId = entity.id.toHexString();
    }

    @Test
    void createProject_persistsAndReturnsSuccess() {
        when(mapper.toEntity(dto)).thenReturn(entity);

        String result = service.createProject(dto);

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
        verify(repository).persist(entity);
    }

    @Test
    void getAllProjects_returnsMappedList() {
        when(repository.listAll()).thenReturn(List.of(entity));
        when(mapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<ProjectDTO> result = service.getAllProjects();

        assertThat(result).hasSize(1).contains(dto);
    }

    @Test
    void updateProject_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        ProjectDTO result = service.updateProject(validId, dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntityFromDTO(dto, entity);
        verify(repository).update(entity);
    }

    @Test
    void updateProject_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateProject(TestDataFactory.INVALID_ID, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateProject_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateProject(validId, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void deleteProject_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));

        String result = service.deleteProject(validId);

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
        verify(repository).deleteById(entity.id);
    }

    @Test
    void deleteProject_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteProject(validId))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }
}

