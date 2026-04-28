package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.ProjectService;
import com.sagar.util.AppConstants;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.ProjectDTO;

@Path("/admin/project")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource extends CommonResource {

    @Inject
    private ProjectService projectService;

    @POST
    public ResponseDTO<String> createProject(@Valid ProjectDTO projectDTO) {
        return buildResponse(AppConstants.PROJECT_CREATED, AppConstants.STATUS_CREATED, projectService.createProject(projectDTO));
    }

    @PATCH
    @Path("/{id}")
    public ResponseDTO<ProjectDTO> updateProject(@PathParam("id") String id, @Valid ProjectDTO projectDTO) {
        return buildResponse(AppConstants.PROJECT_UPDATED, AppConstants.STATUS_OK, projectService.updateProject(id, projectDTO));
    }

    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteProject(@PathParam("id") String id) {
        return buildResponse(AppConstants.PROJECT_DELETED, AppConstants.STATUS_OK, projectService.deleteProject(id));
    }
}
