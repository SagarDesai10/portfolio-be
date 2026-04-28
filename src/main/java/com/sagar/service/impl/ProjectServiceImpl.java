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

@ApplicationScoped
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectRepository repository;

    @Inject
    private ProjectMapper mapper;

    @Override
    public String createProject(ProjectDTO projectDTO) {
        repository.persist(mapper.toEntity(projectDTO));
        return AppConstants.CREATED_SUCCESSFULLY;
    }

    @Override
    public ProjectDTO updateProject(String id, ProjectDTO projectDTO) {
        Project entity = findProject(id);
        mapper.updateEntityFromDTO(projectDTO, entity);
        repository.update(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public String deleteProject(String id) {
        Project entity = findProject(id);
        repository.deleteById(entity.id);
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
        return repository.findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ApplicationException(AppConstants.PROJECT_NOT_FOUND, AppConstants.STATUS_NOT_FOUND));
    }
}
