package com.example.cloud.licenses.services;

import com.example.cloud.common.model.License;
import com.example.cloud.common.model.Organization;
import com.example.cloud.licenses.client.OrganizationDiscoveryClient;
import com.example.cloud.licenses.client.OrganizationFeignClient;
import com.example.cloud.licenses.client.OrganizationRestTemplateClient;
import com.example.cloud.licenses.config.ServiceConfig;
import com.example.cloud.licenses.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    private final ServiceConfig config;

    private final OrganizationFeignClient organizationFeignClient;

    private final OrganizationRestTemplateClient organizationRestClient;

    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    public LicenseService(LicenseRepository licenseRepository, ServiceConfig config,
                          OrganizationFeignClient organizationFeignClient,
                          OrganizationRestTemplateClient organizationRestClient,
                          OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.licenseRepository = licenseRepository;
        this.config = config;
        this.organizationFeignClient = organizationFeignClient;
        this.organizationRestClient = organizationRestClient;
        this.organizationDiscoveryClient = organizationDiscoveryClient;
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization;
        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }
        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization org = retrieveOrgInfo(organizationId, clientType);
        return license
            .withOrganizationName(org.getName())
            .withContactName(org.getContactName())
            .withContactEmail(org.getContactEmail())
            .withContactPhone(org.getContactPhone())
            .withComment(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.delete(license.getLicenseId());
    }
}
