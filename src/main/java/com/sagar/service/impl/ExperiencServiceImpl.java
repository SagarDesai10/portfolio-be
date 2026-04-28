package com.sagar.service.impl;

import com.sagar.entity.Experience;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.ExperienceMapper;
import com.sagar.repository.ExperienceRepository;
import com.sagar.service.ExperienceService;
import com.sagar.util.AppConstants;
import com.sagar.validation.DateRange;
import com.sagar.validation.DateRangeOverlapValidator;
import com.sagar.validation.DateRangeParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.ExperienceDTO;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class ExperiencServiceImpl implements ExperienceService {

    @Inject
    private ExperienceRepository repository;

    @Inject
    private ExperienceMapper mapper;

    @Inject
    private DateRangeOverlapValidator overlapValidator;

    @Override
    public String createExperience(ExperienceDTO experienceDTO) {
        DateRange candidate = buildAndValidateRange(experienceDTO);
        List<Experience> list = repository.listAll();
        overlapValidator.validate(candidate, null, list, "Experience");
        repository.persist(mapper.toEntity(experienceDTO));
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public ExperienceDTO updateExperience(String id, ExperienceDTO experienceDTO) {
        DateRange candidate = buildAndValidateRange(experienceDTO);
        Experience entity = findExperience(id);
        List<Experience> list = repository.listAll();
        overlapValidator.validate(candidate, id, list, "Experience");
        mapper.updateEntityFromDTO(experienceDTO, entity);
        repository.update(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public String deleteExperience(String id) {
        Experience entity = findExperience(id);
        repository.deleteById(entity.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public List<ExperienceDTO> getAllExperiences() {
        return mapper.toDTOList(repository.listAll());
    }

    private Experience findExperience(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        return repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ApplicationException(AppConstants.EXPERIENCE_NOT_FOUND, AppConstants.STATUS_NOT_FOUND));
    }

    private DateRange buildAndValidateRange(ExperienceDTO dto) {
        return new DateRange(
                DateRangeParser.parseRequired(dto.getStartDate(), "startDate"),
                DateRangeParser.parseEndDate(dto.getEndDate())
        );
    }
}