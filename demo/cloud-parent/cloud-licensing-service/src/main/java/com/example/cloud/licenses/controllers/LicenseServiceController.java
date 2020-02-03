package com.example.cloud.licenses.controllers;

import com.example.cloud.licenses.model.License;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    @GetMapping("/{licenseId}")
    public License getLicenses(
        @PathVariable final String organizationId,
        @PathVariable final String licenseId) {
        return new License() {{
            setId(licenseId);
            setLicenseType("Seat");
            setProductName("Teleco111");
            setOrganizationId(organizationId);
        }};
    }
}
