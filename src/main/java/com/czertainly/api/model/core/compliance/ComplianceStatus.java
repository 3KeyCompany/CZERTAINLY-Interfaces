package com.czertainly.api.model.core.compliance;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/*
List if possible status for the Compliance checks. This Object will be used only to
define the status of overall compliance. This object should not be used to define
the compliance status of the individual rules
 */
public enum ComplianceStatus {
    OK("ok"),
    NOK("nok"),
    NA("na")
    ;
    @Schema(description = "Compliance Status",
            example = "ok", required = true)
    private String code;

    ComplianceStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static ComplianceStatus findByCode(String code) {
        return Arrays.stream(ComplianceStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown status {}", code)));
    }
}
