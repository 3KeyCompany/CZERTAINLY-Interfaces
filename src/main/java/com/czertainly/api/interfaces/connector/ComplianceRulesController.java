package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.connector.compliance.ComplianceGroupsResponseDto;
import com.czertainly.api.model.connector.compliance.ComplianceRulesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/complianceProvider/{kind}")
@Tag(
        name = "Compliance Rules",
        description = "Compliance Provider rules API. " +
                "Used to get the list of rules provided by the connector. " +
                "These rules will be made available for the users to choose from the list." +
                "To check for the compliance of a certificate, the Connector accepts " +
                "certificate content and the list of rule references. Once the values are received, compliance is " +
                "checked based on the rules."
)
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

public interface ComplianceRulesController {
    @GetMapping(
            path = "/rules",
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get list of rules"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rules retrieved successfully"
                    )
            }
    )
    List<ComplianceRulesResponseDto> getRules(@Parameter(description = "Connector kind") @PathVariable String kind)
            throws IOException, NotFoundException;

    @GetMapping(
            path = "/groups",
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get list of groups"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Groups retrieved successfully"
                    )
            }
    )
    List<ComplianceGroupsResponseDto> getGroups(@Parameter(description = "Connector kind") @PathVariable String kind)
            throws IOException, NotFoundException;

    @GetMapping(
            path = "/groups/{uuid}",
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get list of rules for a group"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rules retrieved successfully"
                    )
            }
    )
    List<ComplianceRulesResponseDto> getGroupRules(@Parameter(description = "Connector kind") @PathVariable String kind,
                                                   @Parameter(description = "Group kind") @PathVariable String uuid)
            throws IOException, NotFoundException;
}