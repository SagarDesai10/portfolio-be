package com.sagar.service;

import org.acme.beans.ExperienceDTO;

import java.util.List;

public interface ExperienceService {
    public String createExperience(ExperienceDTO experienceDTO);
    public ExperienceDTO updateExperience(String id, ExperienceDTO experienceDTO);
    public String deleteExperience(String id);
    public List<ExperienceDTO> getAllExperiences();
}

