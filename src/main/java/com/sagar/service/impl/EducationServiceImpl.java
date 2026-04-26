package com.sagar.service.impl;

import com.sagar.entity.Education;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.EducationMapper;
import com.sagar.repository.EducationRepository;
import com.sagar.service.EducationService;
import com.sagar.util.AppConstants;
import com.sagar.validation.DateRange;
import com.sagar.validation.DateRangeOverlapValidator;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.EducationDTO;
import org.bson.types.ObjectId;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;

@ApplicationScoped
public class EducationServiceImpl implements EducationService {

    @Inject
    private EducationRepository repository;

    @Inject
    private EducationMapper mapper;

    @Inject
    private DateRangeOverlapValidator overlapValidator;

    @Override
    public Uni<String> createEducation(EducationDTO educationDTO) {
        DateRange candidate = buildAndValidateRange(educationDTO);
        return repository.listAll()
                .flatMap(list -> {
                    overlapValidator.validate(candidate, null, list, "Education");
                    return repository.persist(mapper.toEntity(educationDTO));
                })
                .map(e -> AppConstants.CREATED_SUCCESSFULLY);
    }

    @Override
    public Uni<EducationDTO> updateEducation(String id, EducationDTO educationDTO) {
        DateRange candidate = buildAndValidateRange(educationDTO);
        return findEducation(id)
                .flatMap(entity -> repository.listAll()
                        .flatMap(list -> {
                            overlapValidator.validate(candidate, id, list, "Education");
                            mapper.updateEntityFromDTO(educationDTO, entity);
                            return repository.update(entity);
                        }))
                .map(mapper::toDTO);
    }

    @Override
    public Uni<String> deleteEducation(String id) {
        return findEducation(id)
                .flatMap(entity -> repository.deleteById(entity.id))
                .map(deleted -> AppConstants.DELETED_SUCCESSFULLY);
    }

    @Override
    public Uni<List<EducationDTO>> getAllEducations() {
        return repository.listAll()
                .map(mapper::toDTOList);
    }

    private Uni<Education> findEducation(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            return Uni.createFrom().failure(
                    new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST));
        }
        return repository.findByIdOptional(new ObjectId(id))
                .map(opt -> opt.orElseThrow(() ->
                        new ApplicationException(AppConstants.EDUCATION_NOT_FOUND, AppConstants.STATUS_NOT_FOUND)));
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