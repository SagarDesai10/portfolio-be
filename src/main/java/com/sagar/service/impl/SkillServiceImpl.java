package com.sagar.service.impl;

import com.sagar.entity.Skill;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.SkillMapper;
import com.sagar.repository.SkillRepository;
import com.sagar.service.SkillService;
import com.sagar.util.AppConstants;
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
    public String createSkill(SkillDTO skillDTO) {
        repository.persist(mapper.toEntity(skillDTO));
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public SkillDTO updateSkill(String id, SkillDTO skillDTO) {
        Skill entity = findSkill(id);
        mapper.updateEntityFromDTO(skillDTO, entity);
        repository.update(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public String deleteSkill(String id) {
        Skill entity = findSkill(id);
        repository.deleteById(entity.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        return mapper.toDTOList(repository.listAll());
    }

    private Skill findSkill(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        return repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ApplicationException(AppConstants.SKILL_NOT_FOUND, AppConstants.STATUS_NOT_FOUND));
    }
}
