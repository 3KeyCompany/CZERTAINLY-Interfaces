package com.czertainly.api.model.client.cryptography.tokenprofile;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * Class representing RA profile registration request
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddTokenProfileRequestDto {

    @Schema(
            description = "Token Profile name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Token Profile description"
    )
    private String description;

    @Schema(
            description = "List of Attributes to create Token Profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> attributes;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<RequestAttributeDto> customAttributes;

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled",
            defaultValue = "false"
    )
    private boolean enabled;

    @Schema(
            description = "Usages for the Key"
    )
    private List<KeyUsage> usage;
}
