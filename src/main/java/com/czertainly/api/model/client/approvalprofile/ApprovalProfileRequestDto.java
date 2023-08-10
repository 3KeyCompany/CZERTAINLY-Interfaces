package com.czertainly.api.model.client.approvalprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApprovalProfileRequestDto {

    @Schema(description = "Name of the Approval profile",
            example = "ApprovalProfile1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the Approval profile",
            example = "Detail description of the approval profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Enable of the Approval profile",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;

    @Schema(description = "Expiration of the Approval profile in hours",
            example = "30",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer expiry;

    @Schema(description = "List of Approval steps for the Approval profile",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ApprovalStepDto> approvalSteps = new ArrayList<>();
}
