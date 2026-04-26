package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.AboutService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
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
    public Uni<ResponseDTO<String>> createAboutDetails(@Valid AboutDTO aboutDTO) {
        return aboutService.createAbout(aboutDTO)
                .map(r -> buildResponse(AppConstants.ABOUT_CREATED, AppConstants.STATUS_CREATED, r));
    }

    @GET
    public Uni<ResponseDTO<AboutDTO>> getAboutDetails() {
        return aboutService.getAbout()
                .map(r -> buildResponse(AppConstants.ABOUT_FETCHED, AppConstants.STATUS_OK, r));
    }

    @PATCH
    @Path("/{id}")
    public Uni<ResponseDTO<AboutDTO>> updateAboutDetails(@PathParam("id") String id, @Valid AboutDTO aboutDTO) {
        return aboutService.updateAbout(id, aboutDTO)
                .map(r -> buildResponse(AppConstants.ABOUT_UPDATED, AppConstants.STATUS_OK, r));
    }

    @DELETE
    @Path("/{id}")
    public Uni<ResponseDTO<String>> deleteAboutDetails(@PathParam("id") String id) {
        return aboutService.deleteAbout(id)
                .map(r -> buildResponse(AppConstants.ABOUT_DELETED, AppConstants.STATUS_OK, r));
    }
}
