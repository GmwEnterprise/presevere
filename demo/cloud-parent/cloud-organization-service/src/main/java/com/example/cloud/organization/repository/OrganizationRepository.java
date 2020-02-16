package com.example.cloud.organization.repository;

import com.example.cloud.common.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, String> {

    Organization findById(String organizationId);
}
