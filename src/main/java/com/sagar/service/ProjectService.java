package com.sagar.service;

import org.acme.beans.ProjectDTO;

import java.util.List;

public interface ProjectService {
    String createProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(String id, ProjectDTO projectDTO);
    String deleteProject(String id);
    List<ProjectDTO> getAllProjects();
}
