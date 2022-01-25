package com.czertainly.api.model.core.acme;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class AcmeProfileDto extends NameAndUuidDto {
    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            required = true)
    private boolean isEnabled;
    @Schema(description = "ACME Profile description")
    private String description;
    @Schema(description = "Terms of Service URL")
    private String termsOfServiceUrl;
    @Schema(description = "Website URL")
    private String websiteUrl;
    @Schema(description = "DNS Resolver IP")
    private String dnsResolverIp;
    @Schema(description = "DNS Resolver Port")
    private String dnsResolverPort;
    @Schema(description = "RA Profile")
    private RaProfileDto raProfile;
    @Schema(description = "Retry Interval for ACME client requests")
    private Integer retryInterval;
    @Schema(description = "New approval needed for terms of service change?")
    private Boolean termsOfServiceChangeApproval;
    @Schema(description = "Order Validity")
    private Integer validity;
    @Schema(description = "ACME Directory URL")
    private String directoryUrl;
    @Schema(description = "Terms of Service Change URL")
    private String termsOfServiceChangeUrl;
    @Schema(description = "Is Contact mandatory for new Accounts?")
    private Boolean insistContact;
    @Schema(description = "Is agreeing to terms of service mandatory for new Account?")
    private Boolean insistTermsOfService;
    @Schema(description = "List of Attributes to issue a new Certificate")
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attribute for certificate revocation")
    private List<ResponseAttributeDto> revokeCertificateAttributes;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getDnsResolverIp() {
        return dnsResolverIp;
    }

    public void setDnsResolverIp(String dnsResolverIp) {
        this.dnsResolverIp = dnsResolverIp;
    }

    public String getDnsResolverPort() {
        return dnsResolverPort;
    }

    public void setDnsResolverPort(String dnsResolverPort) {
        this.dnsResolverPort = dnsResolverPort;
    }

    public RaProfileDto getRaProfile() {
        return raProfile;
    }

    public void setRaProfile(RaProfileDto raProfile) {
        this.raProfile = raProfile;
    }

    public List<ResponseAttributeDto> getIssueCertificateAttributes() {
        return issueCertificateAttributes;
    }

    public void setIssueCertificateAttributes(List<ResponseAttributeDto> issueCertificateAttributes) {
        this.issueCertificateAttributes = issueCertificateAttributes;
    }

    public List<ResponseAttributeDto> getRevokeCertificateAttributes() {
        return revokeCertificateAttributes;
    }

    public void setRevokeCertificateAttributes(List<ResponseAttributeDto> revokeCertificateAttributes) {
        this.revokeCertificateAttributes = revokeCertificateAttributes;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Boolean getTermsOfServiceChangeApproval() {
        return termsOfServiceChangeApproval;
    }

    public void setTermsOfServiceChangeApproval(Boolean termsOfServiceChangeApproval) {
        this.termsOfServiceChangeApproval = termsOfServiceChangeApproval;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInsistContact() {
        return insistContact;
    }

    public void setInsistContact(Boolean insistContact) {
        this.insistContact = insistContact;
    }

    public Boolean getInsistTermsOfService() {
        return insistTermsOfService;
    }

    public void setInsistTermsOfService(Boolean insistTermsOfService) {
        this.insistTermsOfService = insistTermsOfService;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getTermsOfServiceChangeUrl() {
        return termsOfServiceChangeUrl;
    }

    public void setTermsOfServiceChangeUrl(String termsOfServiceChangeUrl) {
        this.termsOfServiceChangeUrl = termsOfServiceChangeUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public AcmeProfileDto() {
    }
}
