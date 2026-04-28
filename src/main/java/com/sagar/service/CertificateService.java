package com.sagar.service;

import org.acme.beans.CertificateDTO;

import java.util.List;

public interface CertificateService {
    String createCertificate(CertificateDTO certificateDTO);
    CertificateDTO updateCertificate(String id, CertificateDTO certificateDTO);
    String deleteCertificate(String id);
    List<CertificateDTO> getAllCertificates();
}
