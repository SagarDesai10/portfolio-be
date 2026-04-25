package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.ExperienceService;
import com.sagar.util.AppConstants;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.ExperienceDTO;

@Path("/admin/experience")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExperienceResource extends CommonResource {

    @Inject
    private ExperienceService service;

    @POST
    public ResponseDTO<String> createExperience(@Valid ExperienceDTO experienceDTO) {
        return buildResponse(AppConstants.EXPERIENCE_CREATED, AppConstants.STATUS_CREATED, service.createExperience(experienceDTO));
    }

    @PATCH
    @Path("/{id}")
    public ResponseDTO<ExperienceDTO> updateExperience(@PathParam("id") String id, @Valid ExperienceDTO experienceDTO) {
        return buildResponse(AppConstants.EXPERIENCE_UPDATED, AppConstants.STATUS_OK, service.updateExperience(id, experienceDTO));
    }

    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteExperience(@PathParam("id") String id) {
        return buildResponse(AppConstants.EXPERIENCE_DELETED, AppConstants.STATUS_OK, service.deleteExperience(id));
    }
}
