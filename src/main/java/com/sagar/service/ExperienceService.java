package com.sagar.service;

import io.smallrye.mutiny.Uni;
import org.acme.beans.ExperienceDTO;

import java.util.List;

public interface ExperienceService {
    Uni<String> createExperience(ExperienceDTO experienceDTO);
    Uni<ExperienceDTO> updateExperience(String id, ExperienceDTO experienceDTO);
    Uni<String> deleteExperience(String id);
    Uni<List<ExperienceDTO>> getAllExperiences();
}
