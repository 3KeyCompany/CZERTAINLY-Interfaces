package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.client.AddClientRequestDto;
import com.czertainly.api.model.client.client.EditClientRequestDto;
import com.czertainly.api.model.common.BulkActionMessageDto;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.client.ClientDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v1/clients")
@Tag(name = "Client Management API", description = "Client Management API")
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
                ),
                @ApiResponse(
                        responseCode = "502",
                        description = "Connector Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "503",
                        description = "Connector Communication Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
        })

public interface ClientManagementController {

    @Operation(summary = "Get the list of all clients")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client list retrieved")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    public List<ClientDto> listClients();

    @Operation(summary = "Add a new client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New client added", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addClient(@RequestBody AddClientRequestDto request)
            throws CertificateException, AlreadyExistException, NotFoundException, ValidationException;

    @Operation(summary = "Get the details of a client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    public ClientDto getClient(@Parameter(description = "Client UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Edit a client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client edited")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {
            "application/json"})
    public ClientDto editClient(@Parameter(description = "Client UUID") @PathVariable String uuid, @RequestBody EditClientRequestDto request)
            throws CertificateException, NotFoundException, AlreadyExistException;

    @Operation(summary = "Remove a client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client Deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClient(@Parameter(description = "Client UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Remove multiple client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Clients Deleted")})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<BulkActionMessageDto>> bulkRemoveClient(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Client UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                     @RequestBody List<String> clientUuids) throws NotFoundException;

    @Operation(summary = "Get Authorized profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of authorized profiles")})
    @RequestMapping(path = "/{uuid}/listauth", method = RequestMethod.GET, produces = {"application/json"})
    public List<SimplifiedRaProfileDto> listAuthorizations(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Disable a client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableClient(@Parameter(description = "Client UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Enable a client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PUT, consumes = {
            "application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableClient(@Parameter(description = "Client UUID") @PathVariable String uuid) throws NotFoundException, CertificateException;

    @Operation(summary = "Disable multiple client")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Client disabled")})
    @RequestMapping(path = "/disable", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkDisableClient(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "List of client UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                  @RequestBody List<String> clientUuids) throws NotFoundException;

    @Operation(summary = "Enable multiple client")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Client enabled")})
    @RequestMapping(path = "/enable", method = RequestMethod.PUT, consumes = {
            "application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkEnableClient(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "List of client UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
									 @RequestBody List<String> clientUuids) throws NotFoundException;

    @Operation(summary = "Authorize a client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client authorized with given profile")})
    @RequestMapping(path = "/{uuid}/authorize/{raProfileUuid}", method = RequestMethod.PUT, consumes = {
            "application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void authorizeClient(@Parameter(description = "Client UUID") @PathVariable String uuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Unauthorize Client")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Client unauthorized with given profile")})
    @RequestMapping(path = "/{uuid}/unauthorize/{raProfileUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unauthorizeClient(@Parameter(description = "Client UUID") @PathVariable String uuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;
}
