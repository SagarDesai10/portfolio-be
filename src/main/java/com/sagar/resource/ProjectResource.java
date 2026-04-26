package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.ProjectService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
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
    public Uni<ResponseDTO<String>> createProject(@Valid ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO)
                .map(r -> buildResponse(AppConstants.PROJECT_CREATED, AppConstants.STATUS_CREATED, r));
    }

    @PATCH
    @Path("/{id}")
    public Uni<ResponseDTO<ProjectDTO>> updateProject(@PathParam("id") String id, @Valid ProjectDTO projectDTO) {
        return projectService.updateProject(id, projectDTO)
                .map(r -> buildResponse(AppConstants.PROJECT_UPDATED, AppConstants.STATUS_OK, r));
    }

    @DELETE
    @Path("/{id}")
    public Uni<ResponseDTO<String>> deleteProject(@PathParam("id") String id) {
        return projectService.deleteProject(id)
                .map(r -> buildResponse(AppConstants.PROJECT_DELETED, AppConstants.STATUS_OK, r));
    }
}
