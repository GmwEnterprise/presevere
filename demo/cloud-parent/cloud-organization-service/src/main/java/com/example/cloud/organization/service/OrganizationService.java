package com.example.cloud.organization.service;

import com.example.cloud.common.model.Organization;
import com.example.cloud.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {
    private final OrganizationRepository orgRepository;

    public OrganizationService(OrganizationRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId);
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());
        orgRepository.save(org);
    }

    public void updateOrg(Organization org) {
        orgRepository.save(org);
    }

    public void deleteOrg(Organization org) {
        orgRepository.delete(org.getId());
    }
}
