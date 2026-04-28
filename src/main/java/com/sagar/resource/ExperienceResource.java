package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.ExperienceService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
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
    public Uni<ResponseDTO<String>> createExperience(@Valid ExperienceDTO experienceDTO) {
        return Uni.createFrom().deferred(() -> service.createExperience(experienceDTO))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EXPERIENCE_CREATED, AppConstants.STATUS_CREATED, r));
    }

    @PATCH
    @Path("/{id}")
    public Uni<ResponseDTO<ExperienceDTO>> updateExperience(@PathParam("id") String id, @Valid ExperienceDTO experienceDTO) {
        return Uni.createFrom().deferred(() -> service.updateExperience(id, experienceDTO))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EXPERIENCE_UPDATED, AppConstants.STATUS_OK, r));
    }

    @DELETE
    @Path("/{id}")
    public Uni<ResponseDTO<String>> deleteExperience(@PathParam("id") String id) {
        return Uni.createFrom().deferred(() -> service.deleteExperience(id))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EXPERIENCE_DELETED, AppConstants.STATUS_OK, r));
    }
}
