package com.czertainly.api.model.core.auth;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum Resource {
    NONE("NONE"),

    // GENERAL
    DASHBOARD("dashboard"),
    SETTINGS("settings"),
    AUDIT_LOG("auditLogs"),
    CREDENTIAL("credentials"),
    CONNECTOR("connectors"),

    // AUTH
    USER("users"),
    ROLE("roles"),

    // ACME
    ACME_ACCOUNT("acmeAccounts"),
    ACME_PROFILE("acmeProfiles"),

    // CERTIFICATES
    AUTHORITY("authorities"),
    RA_PROFILE("raProfiles"),
    CERTIFICATE("certificates"),
    GROUP("groups"),
    COMPLIANCE_PROFILE("complianceProfiles"),
    DISCOVERY("discoveries"),

    // ENTITIES
    ENTITY("entities"),
    LOCATION("locations"),

    //CRYPTOGRAPHY
    TOKEN_PROFILE("tokenProfiles"),
    TOKEN("tokens"),
    CRYPTOGRAPHIC_KEY("keys"),

    ATTRIBUTE("attributes");

    @Schema(description = "Resource Name",
            example = "certificates",
            requiredMode = Schema.RequiredMode.REQUIRED)
            
    private final String code;

    Resource(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static Resource findByCode(String code) {
        return Arrays.stream(Resource.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Resource Name {}", code)));
    }
}
