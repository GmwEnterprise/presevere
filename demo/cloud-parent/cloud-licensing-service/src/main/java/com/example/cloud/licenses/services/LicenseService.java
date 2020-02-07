package com.example.cloud.licenses.services;

import com.example.cloud.licenses.config.ServiceConfig;
import com.example.cloud.licenses.model.License;
import com.example.cloud.licenses.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {
    @Resource
    LicenseRepository licenseRepository;
    @Resource(name = "serviceConfig")
    ServiceConfig config;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setComment(config.getExampleProperty());
        return license;
    }

    public List<License> getLicensesByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void save(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }
}
