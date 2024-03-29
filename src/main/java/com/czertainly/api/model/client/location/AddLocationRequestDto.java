package com.czertainly.api.model.client.location;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing Location registration request from clients
 */
public class AddLocationRequestDto {

    @Schema
            (description = "Location name",
            requiredMode = Schema.RequiredMode.REQUIRED
            )
    private String name;

    @Schema(
            description = "Location description"
    )
    private String description;

    @Schema(
            description = "List of Attributes to register Location",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> attributes;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled",
            defaultValue = "false"
    )
    private Boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("description", description)
                .append("attributes", attributes)
                .append("enabled", enabled)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
