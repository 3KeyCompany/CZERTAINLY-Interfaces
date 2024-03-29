package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RuleTriggerRequestDto {

    @Schema(
            description = "Name of the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

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
            description = "Resource associated with the Rule Trigger"
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


    @Schema(
            description = "List of new Rules to add in the Rule Trigger"
    )
    private List<RuleRequestDto> rules;

    @Schema(
            description = "List of UUIDs of existing Rules to add in the Rule Trigger"
    )
    private List<String> rulesUuids;

    @Schema(
            description = "List of new Action Groups to add in the Rule Trigger"
    )
    private List<RuleActionGroupRequestDto> actionGroups;

    @Schema(
            description = "List of UUIDs of existing Action Groups to add in the Rule Trigger"
    )
    private List<String> actionGroupsUuids;

    @Schema(
            description = "List of new Rule Actions to add in the Rule Trigger"
    )
    private List<RuleActionRequestDto> actions;
}
