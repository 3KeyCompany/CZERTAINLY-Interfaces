package com.czertainly.api.model.core.location;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Location
 */
public class LocationDto extends NameAndUuidDto {

    @Schema(description = "Description of the Location")
    private String description;

    @Schema(description = "UUID of Entity instance",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String entityInstanceUuid;

    @Schema(description = "Name of Entity instance",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String entityInstanceName;

    @Schema(description = "List of Location attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;

    @Schema(description = "If the location supports multiple Certificates",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean supportMultipleEntries;

    @Schema(description = "If the location supports key management operations",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean supportKeyManagement;

    @Schema(description = "List of Certificates in Location",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CertificateInLocationDto> certificates;

    @Schema(description = "Location metadata")
    private List<MetadataResponseDto> metadata;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntityInstanceUuid() {
        return entityInstanceUuid;
    }

    public void setEntityInstanceUuid(String entityInstanceUuid) {
        this.entityInstanceUuid = entityInstanceUuid;
    }

    public String getEntityInstanceName() {
        return entityInstanceName;
    }

    public void setEntityInstanceName(String entityInstanceName) {
        this.entityInstanceName = entityInstanceName;
    }

    public List<ResponseAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ResponseAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSupportMultipleEntries() {
        return supportMultipleEntries;
    }

    public void setSupportMultipleEntries(boolean supportMultipleEntries) {
        this.supportMultipleEntries = supportMultipleEntries;
    }

    public boolean isSupportKeyManagement() {
        return supportKeyManagement;
    }

    public void setSupportKeyManagement(boolean supportKeyManagement) {
        this.supportKeyManagement = supportKeyManagement;
    }

    public List<MetadataResponseDto> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataResponseDto> metadata) {
        this.metadata = metadata;
    }

    public List<CertificateInLocationDto> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CertificateInLocationDto> certificates) {
        this.certificates = certificates;
    }

    public List<ResponseAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<ResponseAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("entityInstanceUuid", entityInstanceUuid)
                .append("attributes", attributes)
                .append("customAttributes", customAttributes)
                .append("enabled", enabled)
                .append("entityInstanceName", entityInstanceName)
                .append("supportMultipleEntries", supportMultipleEntries)
                .append("supportKeyManagement", supportKeyManagement)
                .append("metadata", metadata)
                .toString();
    }
}
