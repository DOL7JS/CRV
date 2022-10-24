package cz.upce.nnpro_backend.controllers;

import cz.upce.nnpro_backend.Entities.Owner;
import cz.upce.nnpro_backend.dtos.OwnerDetailOutDto;
import cz.upce.nnpro_backend.dtos.OwnerDto;
import cz.upce.nnpro_backend.services.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
@CrossOrigin
@SecurityRequirement(name = "NNPRO_API")
@Tag(name = "Owner controller")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Operation(summary = "Get owner info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OwnerDetailOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner not found",
                    content = @Content),})
    @GetMapping("/getOwner/{idOwner}")
    public ResponseEntity<?> getOwner(@PathVariable Long idOwner,
                                      @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                      @RequestParam(defaultValue = "car.SPZ") String orderBy,
                                      @RequestParam(defaultValue = "ASC") String orderDirection) {
        return ResponseEntity.ok(ownerService.getOwner(idOwner, page, pageSize, orderBy, orderDirection));
    }

    @Operation(summary = "Get all owners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owners returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OwnerDetailOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner not found",
                    content = @Content),})
    @GetMapping("/getAllOwner")
    public ResponseEntity<?> getOwner() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @Operation(summary = "Add owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Owner.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/addOwner")
    public ResponseEntity<?> addOwner(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(ownerService.addOwner(ownerDto));
    }

    @Operation(summary = "Remove owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner removed and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Owner.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @DeleteMapping("/removeOwner/{idOwner}")
    public ResponseEntity<?> removeOwner(@PathVariable Long idOwner) {
        return ResponseEntity.ok(ownerService.removeOwner(idOwner));
    }

    @Operation(summary = "Edit owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner edited and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Owner.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/editOwner/{idOwner}")
    public ResponseEntity<?> editOwner(@PathVariable Long idOwner, @RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(ownerService.editOwner(idOwner, ownerDto));
    }
}
