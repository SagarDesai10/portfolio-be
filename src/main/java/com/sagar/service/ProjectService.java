package com.sagar.service;

import org.acme.beans.ProjectDTO;

import java.util.List;

public interface ProjectService {
    public String createProject(ProjectDTO projectDTO);
    public ProjectDTO updateProject(String id, ProjectDTO projectDTO);
    public String deleteProject(String id);
    public List<ProjectDTO> getAllProjects();
}

