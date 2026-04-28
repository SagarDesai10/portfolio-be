package com.sagar.service.impl;

import com.sagar.entity.SocialLink;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.SocialLinkMapper;
import com.sagar.repository.SocialLinkRepository;
import com.sagar.service.SocialLinkService;
import com.sagar.util.AppConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.SocialDTO;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class SocialLinkServiceImpl implements SocialLinkService {

    @Inject
    private SocialLinkRepository repository;

    @Inject
    private SocialLinkMapper mapper;

    @Override
    public String createSocialLink(SocialDTO socialDTO) {
        repository.persist(mapper.toEntity(socialDTO));
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public SocialDTO updateSocialLink(String id, SocialDTO socialDTO) {
        SocialLink entity = findSocialLink(id);
        mapper.updateEntityFromDTO(socialDTO, entity);
        repository.update(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public String deleteSocialLink(String id) {
        SocialLink entity = findSocialLink(id);
        repository.deleteById(entity.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public List<SocialDTO> getAllSocialLink() {
        return mapper.toDTOList(repository.listAll());
    }

    private SocialLink findSocialLink(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        return repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ApplicationException(AppConstants.SOCIAL_NOT_FOUND, AppConstants.STATUS_NOT_FOUND));
    }
}
