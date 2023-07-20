
package com.czertainly.api.model.connector.notification;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class NotificationRecipientDto {
    @Schema(description = "Recipient name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Recipient email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;

    @Schema(description = "Mapped attributes values for recipient", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttributeDto> mappedAttributes;
}
