package cz.upce.nnpro_backend.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.services.BranchOfficeService;
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
@RequestMapping("/api/branchOffice")
@CrossOrigin
@SecurityRequirement(name = "NNPRO_API")
@Tag(name = "Branch office controller")
public class BranchOfficeController {
    private final BranchOfficeService branchOfficeService;

    public BranchOfficeController(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    @Operation(summary = "Get branch office info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch office returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchOffice.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Branch office not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Kraj')")
    @GetMapping("/getOffice/{officeId}")
    public ResponseEntity<?> getOffice(@PathVariable Long officeId) {
        return ResponseEntity.ok(branchOfficeService.getOffice(officeId));
    }

    @Operation(summary = "Get all branch offices ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch offices returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchOffice.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content)})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Kraj')")
    @GetMapping("/getAllOffices")
    public ResponseEntity<?> getAllOffices() {
        return ResponseEntity.ok(branchOfficeService.getAllOffices());
    }

    @Operation(summary = "Add branch office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch office added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchOffice.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content)})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Kraj')")
    @PostMapping("/addOffice")
    public ResponseEntity<?> addOffice(@RequestBody @Valid BranchOfficeInDto officeDto) {
        return ResponseEntity.ok(branchOfficeService.addOffice(officeDto));
    }

    @Operation(summary = "Add user to branch office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added to branch office",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Branch office not found or user not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Kraj')")
    @PostMapping("/addUserToOffice")
    public ResponseEntity<?> addUserToOffice(@RequestBody @Valid BranchOfficeUserDto branchOfficeUserDto) {
        return ResponseEntity.ok(branchOfficeService.addUserToOffice(branchOfficeUserDto));
    }

    @Operation(summary = "Remove branch office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch office removed and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchOffice.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Branch office not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Kraj')")
    @DeleteMapping("/removeOffice/{officeId}")
    public ResponseEntity<?> removeOffice(@PathVariable Long officeId) {
        return ResponseEntity.ok(branchOfficeService.removeOffice(officeId));
    }

    @Operation(summary = "Edit branch office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch office edited and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchOffice.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Branch office not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Kraj')")
    @PutMapping("/editOffice/{officeId}")
    public ResponseEntity<?> editOffice(@PathVariable Long officeId, @RequestBody @Valid BranchOfficeInDto officeDto) {
        return ResponseEntity.ok(branchOfficeService.editOffice(officeId, officeDto));
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
