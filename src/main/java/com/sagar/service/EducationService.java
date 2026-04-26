package com.sagar.service;

import io.smallrye.mutiny.Uni;
import org.acme.beans.EducationDTO;

import java.util.List;

public interface EducationService {
    Uni<String> createEducation(EducationDTO educationDTO);
    Uni<EducationDTO> updateEducation(String id, EducationDTO educationDTO);
    Uni<String> deleteEducation(String id);
    Uni<List<EducationDTO>> getAllEducations();
}
