package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.certificate.group.CertificateGroupDto;
import com.czertainly.api.model.core.certificate.group.CertificateGroupRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/groups")
@Tag(name = "Certificate Group API", description = "Certificate Group API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content = @Content
				),
				@ApiResponse(
						responseCode = "404",
						description = "Not Found",
						content = @Content
				),
				@ApiResponse(
						responseCode = "500",
						description = "Internal Server Error",
						content = @Content
				)
		})

public interface CertificateGroupController {
	@Operation(summary = "List Groups")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "list of available Group")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<CertificateGroupDto> listCertificateGroups();
	
	@Operation(summary = "Group details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Group details retrieved") })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public CertificateGroupDto getCertificateGroup(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Create Group")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Group created", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createCertificateGroup(@RequestBody CertificateGroupRequestDto request)
			throws AlreadyExistException, NotFoundException;
	
	@Operation(summary = "Update Group")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Group updated")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public CertificateGroupDto updateCertificateGroup(@Parameter(description = "Group UUID") @PathVariable String uuid, @RequestBody CertificateGroupRequestDto request)
			throws NotFoundException;
	
	@Operation(summary = "Remove Group")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Group removed") })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCertificateGroup(@Parameter(description = "Group UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Remove multiple Groups")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Groups removed") })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveCertificateGroup(@Parameter(description = "Group UUID") @RequestBody List<String> groupUuids) throws NotFoundException;

}