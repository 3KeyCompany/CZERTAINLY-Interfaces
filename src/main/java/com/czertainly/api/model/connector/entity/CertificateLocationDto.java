package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class CertificateLocationDto {

    @Schema(description = "Base64-encoded Certificate content",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String certificateData;

    @Schema(
            description = "Metadata of the Certificate related to the Location"
    )
    private List<MetadataAttribute> metadata;

    @Schema(description = "Type of the Certificate",
            defaultValue = "X509",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private CertificateType certificateType;

    @Schema(description = "If the Certificate in Location has associated private key",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean withKey;

    @Schema(
            description = "List of Attributes to replace Certificate"
    )
    private List<DataAttribute> pushAttributes;

    @Schema(
            description = "List of Attributes to renew Certificate"
    )
    private List<DataAttribute> csrAttributes;

    public String getCertificateData() {
        return certificateData;
    }

    public void setCertificateData(String certificateData) {
        this.certificateData = certificateData;
    }

    public List<MetadataAttribute> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataAttribute> metadata) {
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

    public List<DataAttribute> getPushAttributes() {
        return pushAttributes;
    }

    public void setPushAttributes(List<DataAttribute> pushAttributes) {
        this.pushAttributes = pushAttributes;
    }

    public List<DataAttribute> getCsrAttributes() {
        return csrAttributes;
    }

    public void setCsrAttributes(List<DataAttribute> csrAttributes) {
        this.csrAttributes = csrAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("certificateData", certificateData)
                .append("metadata", metadata)
                .append("certificateType", certificateType)
                .append("pushAttributes", pushAttributes)
                .append("csrAttributes", csrAttributes)
                .append("withKey", withKey)
                .toString();
    }
}
