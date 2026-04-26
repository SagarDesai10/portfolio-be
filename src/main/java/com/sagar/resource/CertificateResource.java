package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.CertificateService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.CertificateDTO;

@Path("/admin/certificate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CertificateResource extends CommonResource {

    @Inject
    private CertificateService service;

    @POST
    public Uni<ResponseDTO<String>> createCertificate(@Valid CertificateDTO certificateDTO) {
        return service.createCertificate(certificateDTO)
                .map(r -> buildResponse(AppConstants.CERTIFICATE_CREATED, AppConstants.STATUS_CREATED, r));
    }

    @PATCH
    @Path("/{id}")
    public Uni<ResponseDTO<CertificateDTO>> updateCertificate(@PathParam("id") String id, @Valid CertificateDTO certificateDTO) {
        return service.updateCertificate(id, certificateDTO)
                .map(r -> buildResponse(AppConstants.CERTIFICATE_UPDATED, AppConstants.STATUS_OK, r));
    }

    @DELETE
    @Path("/{id}")
    public Uni<ResponseDTO<String>> deleteCertificate(@PathParam("id") String id) {
        return service.deleteCertificate(id)
                .map(r -> buildResponse(AppConstants.CERTIFICATE_DELETED, AppConstants.STATUS_OK, r));
    }
}
