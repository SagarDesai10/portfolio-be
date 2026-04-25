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
import java.util.Optional;

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
        About about = mapper.toEntity(aboutDTO);
        repository.persist(about);
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public AboutDTO updateAbout(String id, AboutDTO aboutDTO) {
        About about = findAbout(id);
        mapper.updateEntityFromDTO(aboutDTO, about);
        repository.update(about);
        return enrichWithExperience(mapper.toDTO(about));
    }

    @Override
    public String deleteAbout(String id) {
        About about = findAbout(id);
        repository.deleteById(about.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public AboutDTO getAbout() {
        About about = repository.getAll();
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
        Optional<About> opt = repository.findByIdOptional(new ObjectId(id));
        if (opt.isEmpty()) {
            throw new ApplicationException(AppConstants.ABOUT_NOT_FOUND_BY_ID, AppConstants.STATUS_NOT_FOUND);
        }
        return opt.get();
    }

    /**
     * Calculates total experience in years (as a float, rounded to 1 decimal)
     * by summing up all Experience date ranges.
     *
     * Algorithm:
     *  1. Parse each Experience record's startDate / endDate into a DateRange.
     *  2. Sum the months covered by each range (end inclusive → +1 month).
     *  3. Convert total months → years with one decimal place.
     *
     * Example: jun-2024 → jul-2025 (14 months) + jul-2025 → jan-2026 (7 months)
     *          = 21 months / 12 = 1.75 ≈ 1.8 years
     *
     * Note: The example in the requirement states 1.5 years for the same data,
     * which corresponds to counting months between start and end exclusively
     * (i.e. ChronoUnit.MONTHS.between(start, end) without +1).
     * We use that convention: months = ChronoUnit.MONTHS.between(start, end).
     */
    private AboutDTO enrichWithExperience(AboutDTO dto) {
        List<Experience> experiences = experienceRepository.listAll();
        long totalMonths = 0;
        for (Experience exp : experiences) {
            try {
                DateRange range = exp.getDateRange();
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
