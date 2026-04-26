package com.sagar.service;

import com.sagar.entity.SocialLink;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.SocialLinkMapper;
import com.sagar.repository.SocialLinkRepository;
import com.sagar.service.impl.SocialLinkServiceImpl;
import com.sagar.util.AppConstants;
import com.sagar.util.TestDataFactory;
import io.smallrye.mutiny.Uni;
import org.acme.beans.SocialDTO;
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
class SocialLinkServiceImplTest {

    @Mock SocialLinkRepository repository;
    @Mock SocialLinkMapper     mapper;

    @InjectMocks SocialLinkServiceImpl service;

    private SocialLink entity;
    private SocialDTO  dto;
    private String     validId;

    @BeforeEach
    void setUp() {
        entity  = TestDataFactory.socialLinkEntity();
        dto     = TestDataFactory.socialDTO();
        validId = entity.id.toHexString();
    }

    @Test
    void createSocialLink_persistsAndReturnsSuccess() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.persist(entity)).thenReturn(Uni.createFrom().item(entity));

        String result = service.createSocialLink(dto).await().indefinitely();

        assertThat(result).isEqualTo(AppConstants.CREATED_SUCCESSFULLY);
    }

    @Test
    void getAllSocialLinks_returnsMappedList() {
        when(repository.listAll()).thenReturn(Uni.createFrom().item(List.of(entity)));
        when(mapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<SocialDTO> result = service.getAllSocialLink().await().indefinitely();

        assertThat(result).hasSize(1).contains(dto);
    }

    @Test
    void updateSocialLink_withValidId_updatesAndReturnsDTO() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.of(entity)));
        when(repository.update(entity)).thenReturn(Uni.createFrom().item(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        SocialDTO result = service.updateSocialLink(validId, dto).await().indefinitely();

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntityFromDTO(dto, entity);
    }

    @Test
    void updateSocialLink_withInvalidId_throwsBadRequest() {
        assertThatThrownBy(() -> service.updateSocialLink(TestDataFactory.INVALID_ID, dto).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_BAD_REQUEST);
    }

    @Test
    void updateSocialLink_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.empty()));

        assertThatThrownBy(() -> service.updateSocialLink(validId, dto).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }

    @Test
    void deleteSocialLink_withValidId_deletesAndReturnsSuccess() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.of(entity)));
        when(repository.deleteById(entity.id)).thenReturn(Uni.createFrom().item(true));

        String result = service.deleteSocialLink(validId).await().indefinitely();

        assertThat(result).isEqualTo(AppConstants.DELETED_SUCCESSFULLY);
    }

    @Test
    void deleteSocialLink_withUnknownId_throwsNotFound() {
        when(repository.findByIdOptional(any(ObjectId.class))).thenReturn(Uni.createFrom().item(Optional.empty()));

        assertThatThrownBy(() -> service.deleteSocialLink(validId).await().indefinitely())
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_NOT_FOUND);
    }
}
