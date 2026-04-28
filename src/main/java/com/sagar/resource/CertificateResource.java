package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.CertificateService;
import com.sagar.util.AppConstants;
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
    public ResponseDTO<String> createCertificate(@Valid CertificateDTO certificateDTO) {
        return buildResponse(AppConstants.CERTIFICATE_CREATED, AppConstants.STATUS_CREATED, service.createCertificate(certificateDTO));
    }

    @PATCH
    @Path("/{id}")
    public ResponseDTO<CertificateDTO> updateCertificate(@PathParam("id") String id, @Valid CertificateDTO certificateDTO) {
        return buildResponse(AppConstants.CERTIFICATE_UPDATED, AppConstants.STATUS_OK, service.updateCertificate(id, certificateDTO));
    }

    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteCertificate(@PathParam("id") String id) {
        return buildResponse(AppConstants.CERTIFICATE_DELETED, AppConstants.STATUS_OK, service.deleteCertificate(id));
    }
}
