package com.czertainly.api.model.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ResourceDetailDto extends ResourceDto {

    @Schema(description = "List of Actions for the Resource", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ActionDto> actions;

    public List<ActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ActionDto> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("uuid", uuid)
                .append("actions", actions)
                .toString();
    }
}
