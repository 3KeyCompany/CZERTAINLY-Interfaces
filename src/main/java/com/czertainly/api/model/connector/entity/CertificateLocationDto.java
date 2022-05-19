package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

public class CertificateLocationDto {

    @Schema(description = "Base64-encoded Certificate content",
            required = true)
    private String certificateData;

    @Schema(
            description = "Metadata of the Certificate related to the Location"
    )
    private Map<String, Object> metadata;

    @Schema(description = "Type of the Certificate",
            defaultValue = "X509",
            required = false)
    private CertificateType certificateType;

    @Schema(description = "If the Certificate in Location has associated private key",
            required = true)
    private boolean withKey;

    public String getCertificateData() {
        return certificateData;
    }

    public void setCertificateData(String certificateData) {
        this.certificateData = certificateData;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public boolean isWithKey() {
        return withKey;
    }

    public void setWithKey(boolean withKey) {
        this.withKey = withKey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("certificateData", certificateData)
                .append("metadata", metadata)
                .append("certificateType", certificateType)
                .append("withKey", withKey)
                .toString();
    }
}
