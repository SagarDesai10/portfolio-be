package com.sagar.service.impl;

import com.sagar.entity.Project;
import com.sagar.exceptions.ApplicationException;
import com.sagar.mapper.ProjectMapper;
import com.sagar.repository.ProjectRepository;
import com.sagar.service.ProjectService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
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
    public Uni<String> createProject(ProjectDTO projectDTO) {
        return repository.persist(mapper.toEntity(projectDTO))
                .map(e -> AppConstants.CREATED_SUCCESSFULLY);
    }

    @Override
    public Uni<ProjectDTO> updateProject(String id, ProjectDTO projectDTO) {
        return findProject(id)
                .flatMap(entity -> {
                    mapper.updateEntityFromDTO(projectDTO, entity);
                    return repository.update(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Uni<String> deleteProject(String id) {
        return findProject(id)
                .flatMap(entity -> repository.deleteById(entity.id))
                .map(deleted -> AppConstants.DELETED_SUCCESSFULLY);
    }

    @Override
    public Uni<List<ProjectDTO>> getAllProjects() {
        return repository.listAll()
                .map(mapper::toDTOList);
    }

    private Uni<Project> findProject(String id) {
        if (id == null || !ObjectId.isValid(id)) {
            return Uni.createFrom().failure(
                    new ApplicationException(AppConstants.INVALID_ID, AppConstants.STATUS_BAD_REQUEST));
        }
        return repository.findByIdOptional(new ObjectId(id))
                .map(opt -> opt.orElseThrow(() ->
                        new ApplicationException(AppConstants.PROJECT_NOT_FOUND, AppConstants.STATUS_NOT_FOUND)));
    }
}
