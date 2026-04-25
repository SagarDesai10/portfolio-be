package com.sagar.service;

import com.sagar.entity.Skill;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.SkillMapper;
import com.sagar.repository.SkillRepository;
import com.sagar.service.impl.SkillServiceImpl;
import com.sagar.util.AppConstants;
import com.sagar.util.TestDataFactory;
import org.acme.beans.SkillDTO;
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
class SkillServiceImplTest {

    @Mock SkillRepository repository;
    @Mock SkillMapper     mapper;

    @InjectMocks SkillServiceImpl service;

    private Skill    entity;
    private SkillDTO dto;
    private String   validId;

    @BeforeEach
    void setUp() {
        entity  = TestDataFactory.skillEntity();
        dto     = TestDataFactory.skillDTO();
        validId = entity.id.toHexString();
    }

    @Test
    void createSkill_persistsAndReturnsSuccess() {
        when(mapper.toEntity(dto)).thenReturn(entity);

        String result = service.createSkill(dto);

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
        verify(repository).persist(entity);
    }

    @Test
    void getAllSkills_returnsMappedList() {
        when(repository.listAll()).thenReturn(List.of(entity));
        when(mapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<SkillDTO> result = service.getAllSkills();

        assertThat(result).hasSize(1).contains(dto);
    }

    @Test
    void updateSkill_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        SkillDTO result = service.updateSkill(validId, dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntityFromDTO(dto, entity);
        verify(repository).update(entity);
    }

    @Test
    void updateSkill_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateSkill(TestDataFactory.INVALID_ID, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateSkill_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateSkill(validId, dto))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void deleteSkill_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.of(entity));

        String result = service.deleteSkill(validId);

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
        verify(repository).deleteById(entity.id);
    }

    @Test
    void deleteSkill_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteSkill(validId))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }
}

