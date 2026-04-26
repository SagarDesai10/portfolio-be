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
import io.smallrye.mutiny.Uni;
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
    public Uni<String> createAbout(AboutDTO aboutDTO) {
        return repository.count()
                .flatMap(count -> {
                    if (count > 0) {
                        return Uni.createFrom().failure(
                                new ApplicationException(AppConstants.ABOUT_ALREADY_EXISTS, AppConstants.STATUS_CONFLICT));
                    }
                    return repository.persist(mapper.toEntity(aboutDTO));
                })
                .map(e -> AppConstants.CREATED_SUCCESSFULLY);
    }

    @Override
    public Uni<AboutDTO> updateAbout(String id, AboutDTO aboutDTO) {
        return findAbout(id)
                .flatMap(about -> {
                    mapper.updateEntityFromDTO(aboutDTO, about);
                    return repository.update(about);
                })
                .map(mapper::toDTO)
                .flatMap(this::enrichWithExperience);
    }

    @Override
    public Uni<String> deleteAbout(String id) {
        return findAbout(id)
                .flatMap(about -> repository.deleteById(about.id))
                .map(deleted -> AppConstants.DELETED_SUCCESSFULLY);
    }

    @Override
    public Uni<AboutDTO> getAbout() {
        return repository.findAll().firstResult()
                .flatMap(about -> {
                    if (about == null) {
                        return Uni.createFrom().failure(
                                new ApplicationException(AppConstants.ABOUT_NOT_FOUND, AppConstants.STATUS_NOT_FOUND));
                    }
                    return enrichWithExperience(mapper.toDTO(about));
                });
    }

    // ── helpers ────────────────────────────────────────────────────────────────

    private Uni<About> findAbout(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            return Uni.createFrom().failure(
                    new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST));
        }
        return repository.findByIdOptional(new ObjectId(id))
                .map(opt -> opt.orElseThrow(() ->
                        new ApplicationException(AppConstants.ABOUT_NOT_FOUND_BY_ID, AppConstants.STATUS_NOT_FOUND)));
    }

    private Uni<AboutDTO> enrichWithExperience(AboutDTO dto) {
        return experienceRepository.listAll()
                .map(experiences -> {
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
                });
    }
}
