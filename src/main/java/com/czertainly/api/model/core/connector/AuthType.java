package com.czertainly.api.model.core.connector;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum AuthType implements IPlatformEnum {
    NONE("none", "None"),
    BASIC("basic", "Basic"),
    CERTIFICATE("certificate", "Certificate"),
    API_KEY("apiKey", "API Key"),
    JWT("jwt", "JWT token");

    private final String code;
    private final String label;
    private final String description;

    AuthType(String code, String label) {
        this(code, label,null);
    }

    AuthType(String code, String label, String description) {
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
    public static AuthType findByCode(String code) {
        return Arrays.stream(values())
                .filter(a -> a.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Auth Type " + code));
    }
}
