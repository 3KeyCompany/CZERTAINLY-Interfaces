package com.czertainly.api.interfaces.connector.cryptography;

import com.czertainly.api.exception.CryptographicOperationException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.operations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cryptographyProvider/tokens/{uuid}/keys")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
@Tag(
        name = "Cryptographic Operations",
        description = "Cryptographic Operations API defines operations that can be executed on existing cryptographic Keys."
)
public interface CryptographicOperationsController {

    /////////////////////////////////////////////////////////////////////////////////
    // cipher operations
    /////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "List of cipher Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/{keyUuid}/cipher/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    List<BaseAttribute> listCipherAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate cipher Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Attributes validated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/{keyUuid}/cipher/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseStatus(
            value = HttpStatus.NO_CONTENT
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     * @throws ValidationException Invalid Attributes
     */
    void validateCipherAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Encrypt data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data encrypted"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/{keyUuid}/encrypt",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    EncryptDataResponseDto encryptData(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid,
            @RequestBody CipherDataRequestDto request
    ) throws NotFoundException;

    @Operation(
            summary = "Decrypt data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data decrypted"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/{keyUuid}/decrypt",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    DecryptDataResponseDto decryptData(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid,
            @RequestBody CipherDataRequestDto request
    ) throws NotFoundException;

    /////////////////////////////////////////////////////////////////////////////////
    // signature operations
    /////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "List of signature Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/{keyUuid}/signature/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    List<BaseAttribute> listSignatureAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate signature Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Attributes validated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/{keyUuid}/signature/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseStatus(
            value = HttpStatus.NO_CONTENT
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     * @throws ValidationException Invalid Attributes
     */
    void validateSignatureAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Sign data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data signed"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/{keyUuid}/sign",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    SignDataResponseDto signData(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid,
            @RequestBody SignDataRequestDto request
    ) throws NotFoundException;

    @Operation(
            summary = "Verify data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data decrypted"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/{keyUuid}/verify",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    VerifyDataResponseDto verifyData(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid,
            @RequestBody VerifyDataRequestDto request
    ) throws NotFoundException;

    /////////////////////////////////////////////////////////////////////////////////
    // generate random operations
    /////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "List of random generator Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/random/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance not found
     */
    List<BaseAttribute> listRandomAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate random generator Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Attributes validated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/random/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseStatus(
            value = HttpStatus.NO_CONTENT
    )
    /**
     * @throws NotFoundException Token instance not found
     * @throws ValidationException Invalid Attributes
     */
    void validateRandomAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Generate random data"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Random data generated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/random",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance not found
     */
    RandomDataResponseDto randomData(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody RandomDataRequestDto request
    ) throws NotFoundException;

}
