package com.sagar.service;

import org.acme.beans.EducationDTO;

import java.util.List;

public interface EducationService {
    String createEducation(EducationDTO educationDTO);
    EducationDTO updateEducation(String id, EducationDTO educationDTO);
    String deleteEducation(String id);
    List<EducationDTO> getAllEducations();
}
