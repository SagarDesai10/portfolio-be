package com.sagar.service;

import org.acme.beans.CertificateDTO;

import java.util.List;

public interface CertificateService {
    public String createCertificate(CertificateDTO certificateDTO);
    public CertificateDTO updateCertificate(String id, CertificateDTO certificateDTO);
    public String deleteCertificate(String id);
    public List<CertificateDTO> getAllCertificates();
}

