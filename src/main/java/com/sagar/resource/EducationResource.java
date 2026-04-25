package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.EducationService;
import com.sagar.util.AppConstants;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.EducationDTO;

@Path("/admin/education")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EducationResource extends CommonResource {

    @Inject
    private EducationService educationService;

    @POST
    public ResponseDTO<String> createEducation(@Valid EducationDTO educationDTO) {
        return buildResponse(AppConstants.EDUCATION_CREATED, AppConstants.STATUS_CREATED, educationService.createEducation(educationDTO));
    }

    @PATCH
    @Path("/{id}")
    public ResponseDTO<EducationDTO> updateEducation(@PathParam("id") String id, @Valid EducationDTO educationDTO) {
        return buildResponse(AppConstants.EDUCATION_UPDATED, AppConstants.STATUS_OK, educationService.updateEducation(id, educationDTO));
    }

    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteEducation(@PathParam("id") String id) {
        return buildResponse(AppConstants.EDUCATION_DELETED, AppConstants.STATUS_OK, educationService.deleteEducation(id));
    }
}
