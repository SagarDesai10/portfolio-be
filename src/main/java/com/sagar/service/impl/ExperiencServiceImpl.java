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
import io.smallrye.mutiny.Uni;
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
    public Uni<String> createExperience(ExperienceDTO experienceDTO) {
        DateRange candidate = buildAndValidateRange(experienceDTO);
        return repository.listAll()
                .flatMap(list -> {
                    overlapValidator.validate(candidate, null, list, "Experience");
                    return repository.persist(mapper.toEntity(experienceDTO));
                })
                .map(e -> AppConstants.CREATED_SUCCESSFULLY);
    }

    @Override
    public Uni<ExperienceDTO> updateExperience(String id, ExperienceDTO experienceDTO) {
        DateRange candidate = buildAndValidateRange(experienceDTO);
        return findExperience(id)
                .flatMap(entity -> repository.listAll()
                        .flatMap(list -> {
                            overlapValidator.validate(candidate, id, list, "Experience");
                            mapper.updateEntityFromDTO(experienceDTO, entity);
                            return repository.update(entity);
                        }))
                .map(mapper::toDTO);
    }

    @Override
    public Uni<String> deleteExperience(String id) {
        return findExperience(id)
                .flatMap(entity -> repository.deleteById(entity.id))
                .map(deleted -> AppConstants.DELETED_SUCCESSFULLY);
    }

    @Override
    public Uni<List<ExperienceDTO>> getAllExperiences() {
        return repository.listAll()
                .map(mapper::toDTOList);
    }

    private Uni<Experience> findExperience(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            return Uni.createFrom().failure(
                    new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST));
        }
        return repository.findByIdOptional(new ObjectId(id))
                .map(opt -> opt.orElseThrow(() ->
                        new ApplicationException(AppConstants.EXPERIENCE_NOT_FOUND, AppConstants.STATUS_NOT_FOUND)));
    }

    private DateRange buildAndValidateRange(ExperienceDTO dto) {
        return new DateRange(
                DateRangeParser.parseRequired(dto.getStartDate(), "startDate"),
                DateRangeParser.parseEndDate(dto.getEndDate())
        );
    }
}