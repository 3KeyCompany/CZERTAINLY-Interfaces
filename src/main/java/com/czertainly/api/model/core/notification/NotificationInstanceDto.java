package com.czertainly.api.model.core.notification;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NotificationInstanceDto extends NameAndUuidDto {

    @Schema(description = "Notification instance description")
    private String description;

    @Schema(description = "UUID of Notification provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;

    @Schema(description = "Name of Notification provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    @Schema(description = "Notification Instance Kind", requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(description = "List of Notification instance Attributes", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

    @Schema(description = "List of mapped attributes constructed from mapping attributes and (recipient) custom attributes")
    private List<ResponseAttributeDto> mappedAttributes;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("uuid", uuid)
                .append("name", name)
                .append("attributes", attributes)
                .append("mappedAttributes", mappedAttributes)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .append("kind", kind)
                .toString();
    }
}
