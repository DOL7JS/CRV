package cz.upce.nnpro_backend.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.services.CarService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
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
@RequestMapping("/api/car")
@CrossOrigin
@SecurityRequirement(name = "NNPRO_API")
@OpenAPIDefinition(info = @Info(title = "CRV API", description = """
        Rights on methods:
        - without role: /login
        - with any role(admin,kraj,okres):/addOffice, /getCarByVin, /getCarBySpz, /isCarStoleByVin, /isCarStolenBySpz, all get methods(/getCar/id, etc.)
        - with Admin or Okres rights: all other methods"""))
@Tag(name = "Car controller")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Operation(summary = "Get car info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car not found",
                    content = @Content),})
    @GetMapping("/getCar/{carId}")
    public ResponseEntity<?> getCar(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCar(carId));
    }

    @Operation(summary = "Get all cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car not found",
                    content = @Content),})
    @GetMapping("/getAllCar")
    public ResponseEntity<?> getAllCar() {
        return ResponseEntity.ok(carService.getAllCars());
    }


    @Operation(summary = "Add car to app", description = "It will add car without owner or branch office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "The car's vin already exists.",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(@RequestBody @Valid CarInDto carInDto) throws Exception {
        return ResponseEntity.ok(carService.addCar(carInDto));
    }

    @Operation(summary = "Sign in the car", description = "It will assign car to owner by their ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Owner.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner or car not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/signInCar/")
    public ResponseEntity<?> signInExistingCar(@RequestBody @Valid CarOwnerDto createCarDto) throws Exception {
        return ResponseEntity.ok(carService.changeOwner(createCarDto.getCarId(), createCarDto.getOwnerId()));
    }

    @Operation(summary = "Sign out car", description = "It will delete CONNECTION between car and his owner, car and owner will be still in app")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner returned",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner or car not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/signOutCar/{carId}")
    public CarOutDto signOutCar(@PathVariable Long carId) {
        return carService.signOutCar(carId);
    }


    @Operation(summary = "Edit car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car not found or new vin already exists",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/editCar/{carId}")
    public ResponseEntity<?> editCar(@PathVariable Long carId, @RequestBody @Valid CarInDto editCar) {
        return ResponseEntity.ok(carService.editCar(carId, editCar));
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
