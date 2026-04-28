package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.EducationService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
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
    public Uni<ResponseDTO<String>> createEducation(@Valid EducationDTO educationDTO) {
        return Uni.createFrom().deferred(() -> educationService.createEducation(educationDTO))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EDUCATION_CREATED, AppConstants.STATUS_CREATED, r));
    }

    @PATCH
    @Path("/{id}")
    public Uni<ResponseDTO<EducationDTO>> updateEducation(@PathParam("id") String id, @Valid EducationDTO educationDTO) {
        return Uni.createFrom().deferred(() -> educationService.updateEducation(id, educationDTO))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EDUCATION_UPDATED, AppConstants.STATUS_OK, r));
    }

    @DELETE
    @Path("/{id}")
    public Uni<ResponseDTO<String>> deleteEducation(@PathParam("id") String id) {
        return Uni.createFrom().deferred(() -> educationService.deleteEducation(id))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EDUCATION_DELETED, AppConstants.STATUS_OK, r));
    }
}
