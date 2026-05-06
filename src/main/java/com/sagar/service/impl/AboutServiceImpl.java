package com.sagar.service.impl;

import com.sagar.entity.About;
import com.sagar.entity.Experience;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.AboutMapper;
import com.sagar.repository.AboutRepository;
import com.sagar.repository.ExperienceRepository;
import com.sagar.service.AboutService;
import com.sagar.util.AppConstants;
import com.sagar.validation.DateRange;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.AboutDTO;
import org.bson.types.ObjectId;

import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class AboutServiceImpl implements AboutService {

    @Inject
    AboutRepository repository;

    @Inject
    ExperienceRepository experienceRepository;

    @Inject
    AboutMapper mapper;

    @Override
    public String createAbout(AboutDTO aboutDTO) {
        if (repository.count() > 0) {
            throw new ApplicationException(AppConstants.ABOUT_ALREADY_EXISTS, AppConstants.STATUS_CONFLICT);
        }
        repository.persist(mapper.toEntity(aboutDTO));
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public AboutDTO updateAbout(String id, AboutDTO aboutDTO) {
        About about = findAbout(id);
        mapper.updateEntityFromDTO(aboutDTO, about);
        repository.update(about);
        AboutDTO dto = mapper.toDTO(about);
        return enrichWithExperience(dto);
    }

    @Override
    public String deleteAbout(String id) {
        About about = findAbout(id);
        repository.deleteById(about.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public AboutDTO getAbout() {
        About about = repository.findAll().firstResult();
        if (about == null) {
            throw new ApplicationException(AppConstants.ABOUT_NOT_FOUND, AppConstants.STATUS_NOT_FOUND);
        }
        return enrichWithExperience(mapper.toDTO(about));
    }

    // ── helpers ────────────────────────────────────────────────────────────────

    private About findAbout(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        return repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ApplicationException(AppConstants.ABOUT_NOT_FOUND_BY_ID, AppConstants.STATUS_NOT_FOUND));
    }

    private AboutDTO enrichWithExperience(AboutDTO dto) {
        List<Experience> experiences = experienceRepository.listAll();
        long totalMonths = 0;
        for (Experience exp : experiences) {
            try {
                DateRange range = exp.resolveDateRange();
                totalMonths += ChronoUnit.MONTHS.between(
                        range.getStart().atDay(1),
                        range.getEnd().atDay(1)
                );
            } catch (Exception ignored) {
                // skip malformed entries
            }
        }
        double years = Math.round((totalMonths / 12.0) * 10.0) / 10.0;
        dto.setExperience(years);
        return dto;
    }
}
