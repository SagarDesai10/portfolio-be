package com.sagar.service.impl;

import com.sagar.entity.Project;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.ProjectMapper;
import com.sagar.repository.ProjectRepository;
import com.sagar.service.ProjectService;
import com.sagar.util.AppConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.beans.ProjectDTO;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectRepository repository;

    @Inject
    private ProjectMapper mapper;

    @Override
    public String createProject(ProjectDTO projectDTO) {
        Project project = mapper.toEntity(projectDTO);
        repository.persist(project);
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public ProjectDTO updateProject(String id, ProjectDTO projectDTO) {
        Project project = findProject(id);
        mapper.updateEntityFromDTO(projectDTO, project);
        repository.update(project);
        return mapper.toDTO(project);
    }

    @Override
    public String deleteProject(String id) {
        Project project = findProject(id);
        repository.deleteById(project.id);
        return AppConstants.DELETED_SUCCESSFULLY;
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return mapper.toDTOList(repository.listAll());
    }

    private Project findProject(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            throw new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST);
        }
        Optional<Project> optProject = repository.findByIdOptional(new ObjectId(id));
        if (optProject.isEmpty()) {
            throw new ApplicationException(AppConstants.PROJECT_NOT_FOUND, AppConstants.STATUS_NOT_FOUND);
        }
        return optProject.get();
    }
}
