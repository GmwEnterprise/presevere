package com.thoughtmechanix.licenses.model;

public class License {
    private String licenseId;
    private String productName;
    private String licenseType;
    private String organizationId;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public License withId(String licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public License withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public License withLicenseType(String licenseType) {
        this.licenseType = licenseType;
        return this;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public License withOrganizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }
}
