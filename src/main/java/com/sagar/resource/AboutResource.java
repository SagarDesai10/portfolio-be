package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.AboutService;
import com.sagar.util.AppConstants;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.AboutDTO;

@Resource
@Path("/admin/about")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AboutResource extends CommonResource {

    @Inject
    AboutService aboutService;

    @POST
    public ResponseDTO<String> createAboutDetails(@Valid AboutDTO aboutDTO) {
        return buildResponse(AppConstants.ABOUT_CREATED, AppConstants.STATUS_CREATED, aboutService.createAbout(aboutDTO));
    }

    @GET
    public ResponseDTO<AboutDTO> getAboutDetails() {
        return buildResponse(AppConstants.ABOUT_FETCHED, AppConstants.STATUS_OK, aboutService.getAbout());
    }

    @PATCH
    @Path("/{id}")
    public ResponseDTO<AboutDTO> updateAboutDetails(@PathParam("id") String id, @Valid AboutDTO aboutDTO) {
        return buildResponse(AppConstants.ABOUT_UPDATED, AppConstants.STATUS_OK, aboutService.updateAbout(id, aboutDTO));
    }

    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteAboutDetails(@PathParam("id") String id) {
        return buildResponse(AppConstants.ABOUT_DELETED, AppConstants.STATUS_OK, aboutService.deleteAbout(id));
    }
}
