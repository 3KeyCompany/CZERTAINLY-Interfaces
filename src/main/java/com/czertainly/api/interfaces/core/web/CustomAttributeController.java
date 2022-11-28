package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.attribute.AttributeDefinitionDto;
import com.czertainly.api.model.client.attribute.CustomAttributeCreateRequestDto;
import com.czertainly.api.model.client.attribute.CustomAttributeDefinitionDetailDto;
import com.czertainly.api.model.client.attribute.CustomAttributeUpdateRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.auth.Resource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/attributes/custom")
@Tag(name = "Custom Attributes", description = "Custom Attributes API")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(schema = @Schema())
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden",
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
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

public interface CustomAttributeController {
    @Operation(summary = "List Custom Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "list of available Custom Attributes")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinitionDto> listAttributes();

    @Operation(summary = "Attribute details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Attribute details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    CustomAttributeDefinitionDetailDto getAttributes(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Create Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Attribute created", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<CustomAttributeDefinitionDetailDto> createAttribute(@RequestBody CustomAttributeCreateRequestDto request)
            throws AlreadyExistException, NotFoundException;

    @Operation(summary = "Edit Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute updated")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    CustomAttributeDefinitionDetailDto editAttribute(@Parameter(description = "Attribute UUID") @PathVariable String uuid, @RequestBody CustomAttributeUpdateRequestDto request)
            throws NotFoundException;

    @Operation(summary = "Delete Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAttribute(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid) throws NotFoundException;


    @Operation(summary = "Enable Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableAttribute(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Disable Custom Attribute")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableAttribute(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete multiple Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Attributes deleted")})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDeleteAttributes(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Attribute UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> attributeUuids);

    @Operation(summary = "Enable multiple Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Attributes enabled")})
    @RequestMapping(method = RequestMethod.PATCH, path = "/enable", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkEnableAttributes(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Attribute UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> attributeUuids);

    @Operation(summary = "Disable multiple Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Attributes disabled")})
    @RequestMapping(method = RequestMethod.PATCH, path = "/disable", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDisableAttributes(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Attribute UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                               @RequestBody List<String> attributeUuids);

    @Operation(summary = "Associate Custom Attribute to Resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Custom Attribute disabled")})
    @RequestMapping(path = "/{uuid}/resource", method = RequestMethod.PATCH, produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateResource(@Parameter(description = "Custom Attribute UUID") @PathVariable String uuid, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "List of Resources", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"raProfiles\",\"authorities\"]")}))
    @RequestBody List<Resource> resources) throws NotFoundException;

    @Operation(summary = "Get Custom Attributes for a resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute retrieved")})
    @RequestMapping(path = "/resources/{resource}", method = RequestMethod.GET, produces = {"application/json"})
    List<BaseAttribute> getResourceAttributes(@Parameter(description = "Resource Name") @PathVariable Resource resource);

    @Operation(summary = "Get available resources for Custom Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Custom Attribute retrieved")})
    @RequestMapping(path = "/resources", method = RequestMethod.GET, produces = {"application/json"})
    List<Resource> getResources();
}
