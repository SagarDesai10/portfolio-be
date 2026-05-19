package com.sagar.service.impl;

import com.sagar.entity.Education;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.EducationMapper;
import com.sagar.repository.EducationRepository;
import com.sagar.service.EducationService;
import com.sagar.util.AppConstants;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.EducationDTO;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class EducationServiceImpl implements EducationService {

    @Inject
    private EducationRepository repository;

    @Inject
    private EducationMapper mapper;

    @Override
    public String createEducation(EducationDTO educationDTO) {
        buildAndValidateRange(educationDTO);
        repository.persist(mapper.toEntity(educationDTO));
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public EducationDTO updateEducation(String id, EducationDTO educationDTO) {
        buildAndValidateRange(educationDTO);
        Education entity = findEducation(id);
        mapper.updateEntityFromDTO(educationDTO, entity);
        repository.update(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public String deleteEducation(String id) {
        Education entity = findEducation(id);
        repository.deleteById(entity.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public List<EducationDTO> getAllEducations() {
        return mapper.toDTOList(repository.listAll(Sort.descending("startYear")));
    }

    private Education findEducation(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        return repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ApplicationException(AppConstants.EDUCATION_NOT_FOUND, AppConstants.STATUS_NOT_FOUND));
    }

    private void buildAndValidateRange(EducationDTO dto) {
        if (dto.getStartYear() == null) {
            throw new ApplicationException("startYear is required", AppConstants.STATUS_BAD_REQUEST);
        }
        if (dto.getEndYear() != null && dto.getEndYear() < dto.getStartYear()) {
            throw new ApplicationException("endYear must be greater than or equal to startYear", AppConstants.STATUS_BAD_REQUEST);
        }
    }
}