package com.czertainly.api.model.connector.cryptography.operations;

import com.czertainly.api.model.connector.cryptography.operations.data.CipherResponseData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DecryptDataResponseDto {

    @Schema(
            description = "Decrypted data",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<CipherResponseData> decryptedData;

}
