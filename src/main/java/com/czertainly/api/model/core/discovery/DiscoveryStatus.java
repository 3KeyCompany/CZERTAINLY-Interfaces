package com.czertainly.api.model.core.discovery;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.connector.cryptography.enums.IAbstractSearchableEnum;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum DiscoveryStatus implements IPlatformEnum, IAbstractSearchableEnum {

    IN_PROGRESS("inProgress", "In Progress"),
    FAILED("failed", "Failed"),
    COMPLETED("completed", "Completed"),
    WARNING("warning", "Warning")
    ;
    @Schema(description = "Discovery Status",
            example = "completed", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    DiscoveryStatus(String code, String label) {
        this(code, label,null);
    }

    DiscoveryStatus(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static DiscoveryStatus findByCode(String code) {
        return Arrays.stream(DiscoveryStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Discovery status {}", code)));
    }

    @Override
    public String getEnumLabel() {
        return this.code;
    }
}
