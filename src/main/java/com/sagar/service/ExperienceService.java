package com.sagar.service;

import org.acme.beans.ExperienceDTO;

import java.util.List;

public interface ExperienceService {
    String createExperience(ExperienceDTO experienceDTO);
    ExperienceDTO updateExperience(String id, ExperienceDTO experienceDTO);
    String deleteExperience(String id);
    List<ExperienceDTO> getAllExperiences();
}
