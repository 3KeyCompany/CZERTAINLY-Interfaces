package com.czertainly.api.model.common.attribute.v2.callback;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum AttributeValueTarget implements IPlatformEnum {

    PATH_VARIABLE("pathVariable", "Path variable"),
    REQUEST_PARAMETER("requestParameter", "Request parameter"),
    BODY("body", "Body property");

    private final String code;

    private final String label;

    private final String description;

    AttributeValueTarget(String code, String label) {
        this(code, label,null);
    }

    AttributeValueTarget(String code, String label, String description) {
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
    public static AttributeValueTarget findByCode(String code) {
        return Arrays.stream(AttributeValueTarget.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Target {}", code)));
    }
}
