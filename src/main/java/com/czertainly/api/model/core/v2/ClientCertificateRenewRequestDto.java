package com.czertainly.api.model.core.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Class representing a request to renew certificate
 */
@Data
public class ClientCertificateRenewRequestDto {

    @Schema(
            description = "True to replace renewed certificate in the associated locations",
            defaultValue = "false"
    )
    public boolean replaceInLocations;
    @Schema(
            description = "Certificate sign request (PKCS#10) encoded as Base64 string. If not provided, CSR attributes will be used"
    )
    private String pkcs10;
    @Schema(
            description = "Use existing CSR",
            defaultValue = "false"
    )
    private boolean useExistingCsr;
    @Schema(
            description = "CSR Attributes. If not provided, existing attributes will be used to generate the new CSR"
    )
    private List<RequestAttributeDto> csrAttributes;
}
