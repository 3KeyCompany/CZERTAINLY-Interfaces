package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class EntityInstanceRequestDto {

    @Schema(description = "Entity instance name",
            required = true)
    private String name;

    @Schema(description = "Kind of Entity instance",
            required = true)
    private String kind;

    @Schema(description = "List of Entity instance Attributes",
            required = true)
    private List<RequestAttributeDto> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("kind", kind)
                .append("name", name)
                .append("attributes", attributes)
                .toString();
    }
}
