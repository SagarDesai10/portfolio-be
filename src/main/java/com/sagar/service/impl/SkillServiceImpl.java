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
import java.util.Optional;

@ApplicationScoped
public class SkillServiceImpl implements SkillService {

    @Inject
    private SkillRepository repository;

    @Inject
    private SkillMapper mapper;

    @Override
    public String createSkill(SkillDTO skillDTO) {
        Skill skill = mapper.toEntity(skillDTO);
        repository.persist(skill);
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public SkillDTO updateSkill(String id, SkillDTO skillDTO) {
        Skill skill = findSkill(id);
        mapper.updateEntityFromDTO(skillDTO, skill);
        repository.update(skill);
        return mapper.toDTO(skill);
    }

    @Override
    public String deleteSkill(String id) {
        Skill skill = findSkill(id);
        repository.deleteById(skill.id);
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
        Optional<Skill> optSkill = repository.findByIdOptional(new ObjectId(id));
        if (optSkill.isEmpty()) {
            throw new ApplicationException(AppConstants.SKILL_NOT_FOUND, AppConstants.STATUS_NOT_FOUND);
        }
        return optSkill.get();
    }
}
