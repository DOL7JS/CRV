package cz.upce.nnpro_backend.controllers;

import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.dtos.ErrorDto;
import cz.upce.nnpro_backend.dtos.OwnerOutDto;
import cz.upce.nnpro_backend.dtos.OwnerInDto;
import cz.upce.nnpro_backend.services.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/owner")
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
                            schema = @Schema(implementation = OwnerOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner not found",
                    content = @Content),})
    @GetMapping("/getOwner/{idOwner}")
    public ResponseEntity<?> getOwner(@PathVariable Long idOwner) {
        return ResponseEntity.ok(ownerService.getOwner(idOwner));
    }

    @Operation(summary = "Get all owners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owners returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OwnerOutDto.class))}),
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
    public ResponseEntity<?> addOwner(@RequestBody @Valid OwnerInDto ownerInDto) {
        return ResponseEntity.ok(ownerService.addOwner(ownerInDto));
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
    public ResponseEntity<?> editOwner(@PathVariable Long idOwner, @RequestBody @Valid OwnerInDto ownerInDto) {
        return ResponseEntity.ok(ownerService.editOwner(idOwner, ownerInDto));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ErrorDto errorDto = new ErrorDto();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorDto.addError(errorMessage);
        });
        return errorDto;
    }
}
