package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.SocialLinkService;
import com.sagar.util.AppConstants;
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
    public ResponseDTO<String> createSocialLink(@Valid SocialDTO socialDTO) {
        return buildResponse(AppConstants.SOCIAL_CREATED, AppConstants.STATUS_CREATED, service.createSocialLink(socialDTO));
    }

    @PATCH
    @Path("/{id}")
    public ResponseDTO<SocialDTO> updateSocialLink(@PathParam("id") String id, @Valid SocialDTO socialDTO) {
        return buildResponse(AppConstants.SOCIAL_UPDATED, AppConstants.STATUS_OK, service.updateSocialLink(id, socialDTO));
    }

    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteSocialLink(@PathParam("id") String id) {
        return buildResponse(AppConstants.SOCIAL_DELETED, AppConstants.STATUS_OK, service.deleteSocialLink(id));
    }
}
