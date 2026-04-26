package com.sagar.service.impl;

import com.sagar.entity.Skill;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.SkillMapper;
import com.sagar.repository.SkillRepository;
import com.sagar.service.SkillService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.SkillDTO;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class SkillServiceImpl implements SkillService {

    @Inject
    private SkillRepository repository;

    @Inject
    private SkillMapper mapper;

    @Override
    public Uni<String> createSkill(SkillDTO skillDTO) {
        return repository.persist(mapper.toEntity(skillDTO))
                .map(e -> AppConstants.CREATED_SUCCESSFULLY);
    }

    @Override
    public Uni<SkillDTO> updateSkill(String id, SkillDTO skillDTO) {
        return findSkill(id)
                .flatMap(entity -> {
                    mapper.updateEntityFromDTO(skillDTO, entity);
                    return repository.update(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Uni<String> deleteSkill(String id) {
        return findSkill(id)
                .flatMap(entity -> repository.deleteById(entity.id))
                .map(deleted -> AppConstants.DELETED_SUCCESSFULLY);
    }

    @Override
    public Uni<List<SkillDTO>> getAllSkills() {
        return repository.listAll()
                .map(mapper::toDTOList);
    }

    private Uni<Skill> findSkill(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            return Uni.createFrom().failure(
                    new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST));
        }
        return repository.findByIdOptional(new ObjectId(id))
                .map(opt -> opt.orElseThrow(() ->
                        new ApplicationException(AppConstants.SKILL_NOT_FOUND, AppConstants.STATUS_NOT_FOUND)));
    }
}
