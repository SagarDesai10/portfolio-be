package com.sagar.service;

import io.smallrye.mutiny.Uni;
import org.acme.beans.CertificateDTO;

import java.util.List;

public interface CertificateService {
    Uni<String> createCertificate(CertificateDTO certificateDTO);
    Uni<CertificateDTO> updateCertificate(String id, CertificateDTO certificateDTO);
    Uni<String> deleteCertificate(String id);
    Uni<List<CertificateDTO>> getAllCertificates();
}
