package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * List of the parameters for the challenge object in the
 * ACME protocol
 */
public class Challenge {
    /**
     * Type of the challenge encoded in the object.
     */
    @Schema(description = "Type of Challenge. Either http-01 or dns-01",
            required = true)
    private ChallengeType type;

    /**
     * URL to which the response can be posted after the client completes the Challenge
     */
    @Schema(description = "URL to which the response can be posted after the client completes the Challenge",
            required = true)
    private String url;

    /**
     * Status of the challenge. The possible values are "pending", "processing", "valid", "invalid"
     * This is a mandatory field.
     */
    @Schema(description = "Challenge status",
            required = true)
    private ChallengeStatus status;

    /**
     * Time at which the server validated this challenge. This data is encoded in the format
     * specified in RFC3339.
     * This is a Non-Mandatory parameter bus is required if the status of the status field is set to valid
     */
    @Schema(description = "Timestamp at which the Challenge is validated")
    private String validated;

    /**
     * Error that occurred while the server was validating the challenge, if any, structured as a problem document
     * [RFC7807].  Multiple errors can be indicated by using subproblems.
     * A challenge object with an error MUST have status equal to "invalid".
     */
    @Schema(description = "Errors in Challenge validation")
    private ProblemDocument error;

    /**
     * Random string generated using the SecureRandom class of JAVA to provide a cryptography random key
     */
    @Schema(description = "Token for the Challenge",
            required = true)
    private String token;

    public ChallengeType getType() {
        return type;
    }

    public void setType(ChallengeType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ChallengeStatus getStatus() {
        return status;
    }

    public void setStatus(ChallengeStatus status) {
        this.status = status;
    }

    public String getValidated() {
        return validated;
    }

    public void setValidated(String validated) {
        this.validated = validated;
    }

    public ProblemDocument getError() {
        return error;
    }

    public void setError(ProblemDocument error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
