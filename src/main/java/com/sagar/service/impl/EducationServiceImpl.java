package com.sagar.service.impl;

import com.sagar.entity.Education;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.EducationMapper;
import com.sagar.repository.EducationRepository;
import com.sagar.service.EducationService;
import com.sagar.util.AppConstants;
import com.sagar.validation.DateRange;
import com.sagar.validation.DateRangeOverlapValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.EducationDTO;
import org.bson.types.ObjectId;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EducationServiceImpl implements EducationService {

    @Inject
    private EducationRepository repository;

    @Inject
    private EducationMapper mapper;

    @Inject
    private DateRangeOverlapValidator overlapValidator;

    @Override
    public String createEducation(EducationDTO educationDTO) {
        DateRange candidate = buildAndValidateRange(educationDTO);
        overlapValidator.validate(candidate, null, repository.listAll(), "Education");
        Education education = mapper.toEntity(educationDTO);
        repository.persist(education);
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public EducationDTO updateEducation(String id, EducationDTO educationDTO) {
        Education education = findEducation(id);
        DateRange candidate = buildAndValidateRange(educationDTO);
        overlapValidator.validate(candidate, id, repository.listAll(), "Education");
        mapper.updateEntityFromDTO(educationDTO, education);
        repository.update(education);
        return mapper.toDTO(education);
    }

    @Override
    public String deleteEducation(String id) {
        Education education = findEducation(id);
        repository.deleteById(education.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public List<EducationDTO> getAllEducations() {
        return mapper.toDTOList(repository.listAll());
    }

    private Education findEducation(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        Optional<Education> optEducation = repository.findByIdOptional(new ObjectId(id));
        if (optEducation.isEmpty()) {
            throw new ApplicationException(AppConstants.EDUCATION_NOT_FOUND, AppConstants.STATUS_NOT_FOUND);
        }
        return optEducation.get();
    }

    private DateRange buildAndValidateRange(EducationDTO dto) {
        if (dto.getStartYear() == null) {
            throw new ApplicationException("startYear is required", AppConstants.STATUS_BAD_REQUEST);
        }
        int startYearInt;
        try {
            startYearInt = dto.getStartYear();
        } catch (NumberFormatException e) {
            throw new ApplicationException("startYear must be a valid year (e.g. 2020)", AppConstants.STATUS_BAD_REQUEST);
        }
        YearMonth start = YearMonth.of(startYearInt, Month.JANUARY);
        YearMonth end;
        if (dto.getEndYear() == null) {
            end = YearMonth.now();
        } else {
            int endYearInt;
            try {
                endYearInt = dto.getEndYear();
            } catch (NumberFormatException e) {
                throw new ApplicationException("endYear must be a valid year (e.g. 2024)", AppConstants.STATUS_BAD_REQUEST);
            }
            end = YearMonth.of(endYearInt, Month.DECEMBER);
        }

        return new DateRange(start, end);
    }
}