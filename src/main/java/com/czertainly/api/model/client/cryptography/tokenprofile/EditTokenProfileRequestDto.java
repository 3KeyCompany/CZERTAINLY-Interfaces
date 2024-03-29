package com.czertainly.api.model.client.cryptography.tokenprofile;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditTokenProfileRequestDto {

    @Schema(
            description = "Description of Token Profile"
    )
    private String description;

    @Schema(
            description = "List of Attributes for Token Profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> attributes;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<RequestAttributeDto> customAttributes;

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled"
    )
    private Boolean enabled;

    @Schema(
            description = "Usages for the Key"
    )
    private List<KeyUsage> usage;
}
