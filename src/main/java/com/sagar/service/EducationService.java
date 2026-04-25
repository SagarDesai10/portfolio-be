package com.sagar.service;

import org.acme.beans.EducationDTO;

import java.util.List;

public interface EducationService {
    public String createEducation(EducationDTO educationDTO);
    public EducationDTO updateEducation(String id, EducationDTO educationDTO);
    public String deleteEducation(String id);
    public List<EducationDTO> getAllEducations();
}

