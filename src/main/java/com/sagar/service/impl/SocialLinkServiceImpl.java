package com.sagar.service.impl;

import com.sagar.entity.SocialLink;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.SocialLinkMapper;
import com.sagar.repository.SocialLinkRepository;
import com.sagar.service.SocialLinkService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
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
    public Uni<String> createSocialLink(SocialDTO socialDTO) {
        return repository.persist(mapper.toEntity(socialDTO))
                .map(e -> AppConstants.CREATED_SUCCESSFULLY);
    }

    @Override
    public Uni<SocialDTO> updateSocialLink(String id, SocialDTO socialDTO) {
        return findSocialLink(id)
                .flatMap(entity -> {
                    mapper.updateEntityFromDTO(socialDTO, entity);
                    return repository.update(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Uni<String> deleteSocialLink(String id) {
        return findSocialLink(id)
                .flatMap(entity -> repository.deleteById(entity.id))
                .map(deleted -> AppConstants.DELETED_SUCCESSFULLY);
    }

    @Override
    public Uni<List<SocialDTO>> getAllSocialLink() {
        return repository.listAll()
                .map(mapper::toDTOList);
    }

    private Uni<SocialLink> findSocialLink(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            return Uni.createFrom().failure(
                    new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST));
        }
        return repository.findByIdOptional(new ObjectId(id))
                .map(opt -> opt.orElseThrow(() ->
                        new ApplicationException(AppConstants.SOCIAL_NOT_FOUND, AppConstants.STATUS_NOT_FOUND)));
    }
}
