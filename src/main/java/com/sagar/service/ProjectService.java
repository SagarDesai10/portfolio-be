package com.sagar.service;

import io.smallrye.mutiny.Uni;
import org.acme.beans.ProjectDTO;

import java.util.List;

public interface ProjectService {
    Uni<String> createProject(ProjectDTO projectDTO);
    Uni<ProjectDTO> updateProject(String id, ProjectDTO projectDTO);
    Uni<String> deleteProject(String id);
    Uni<List<ProjectDTO>> getAllProjects();
}
