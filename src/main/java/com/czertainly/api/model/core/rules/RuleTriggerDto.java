package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class RuleTriggerDto extends NameAndUuidDto {

    @Schema(
            description = "Description of the Rule Trigger"
    )
    private String description;

    @Schema(
            description = "Type of the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private RuleTriggerType triggerType;


    @Schema(
            description = "Name of the event of the Rule Trigger"
    )
    private String eventName;

    @Schema(
            description = "Resource associated with the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;

    @Schema(
            description = "Type of the the Rule Trigger event source object"
    )
    private Resource triggerResource;

    @Schema(
            description = "UUID of the the Rule Trigger event source object"
    )
    private String triggerResourceUuid;

}
