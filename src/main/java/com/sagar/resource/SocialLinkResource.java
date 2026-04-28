package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.SocialLinkService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.SocialDTO;

@Path("/admin/social-link")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SocialLinkResource extends CommonResource {

    @Inject
    private SocialLinkService service;

    @POST
    public Uni<ResponseDTO<String>> createSocialLink(@Valid SocialDTO socialDTO) {
        return Uni.createFrom().deferred(() -> service.createSocialLink(socialDTO))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.SOCIAL_CREATED, AppConstants.STATUS_CREATED, r));
    }

    @PATCH
    @Path("/{id}")
    public Uni<ResponseDTO<SocialDTO>> updateSocialLink(@PathParam("id") String id, @Valid SocialDTO socialDTO) {
        return Uni.createFrom().deferred(() -> service.updateSocialLink(id, socialDTO))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.SOCIAL_UPDATED, AppConstants.STATUS_OK, r));
    }

    @DELETE
    @Path("/{id}")
    public Uni<ResponseDTO<String>> deleteSocialLink(@PathParam("id") String id) {
        return Uni.createFrom().deferred(() -> service.deleteSocialLink(id))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.SOCIAL_DELETED, AppConstants.STATUS_OK, r));
    }
}
