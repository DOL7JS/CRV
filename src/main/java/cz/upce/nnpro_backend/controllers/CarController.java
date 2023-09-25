package cz.upce.nnpro_backend.controllers;


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
@RequestMapping("/car")
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


    @Operation(summary = "Get car info by vin", description = "It's prepared for STK app")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car not found",
                    content = @Content),})
    @GetMapping("/getCarByVin/{vin}")//pripraveno pro STK
    public ResponseEntity<?> getCarByVin(@PathVariable String vin) {
        return ResponseEntity.ok(carService.getCarByVin(vin));
    }

    @Operation(summary = "Get car info by spz", description = "It's prepared for STK app")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarOutDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car not found",
                    content = @Content),})
    @GetMapping("/getCarBySpz/{spz}")//pripraveno pro STK
    public ResponseEntity<?> getCarBySPZ(@PathVariable String spz) {
        return ResponseEntity.ok(carService.getCarBySPZ(spz));
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

    @Operation(summary = "Sign in the car", description = "It will add car and assign it to owner by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Owner.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/signInCar/{ownerId}")
    public ResponseEntity<?> signInCar(@RequestBody @Valid CarInDto carInDto, @PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(carService.signInCar(carInDto, ownerId));
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
        return ResponseEntity.ok(carService.signInExistingCar(createCarDto));
    }

    @Operation(summary = "Change owner of car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Owner.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner or car not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/changeOwner")
    public ResponseEntity<?> changeOwner(@RequestBody @Valid CarOwnerDto carOwnerDto) {
        return ResponseEntity.ok(carService.changeOwner(carOwnerDto.getCarId(), carOwnerDto.getOwnerId()));
    }

    @Operation(summary = "Sign out car", description = "It will delete CONNECTION between car and his owner, car and owner will be still in app")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner returned",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Owner or car not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/signOutCar")
    public void signOutCar(@RequestBody @Valid CarOwnerDto carOwnerDto) {
        carService.signOutCar(carOwnerDto);
    }

    @Operation(summary = "Put car in deposit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/putToDeposit/{carId}")
    public ResponseEntity<?> putCarToDeposit(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.putCarInDeposit(carId));
    }

    @Operation(summary = "Add car to office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car or branch office not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping({"/addCarToOffice", "editCarInOffice"})
    public ResponseEntity<?> addCarToOffice(@RequestBody @Valid CarBranchOfficeDto officeDto) {
        return ResponseEntity.ok(carService.addCarToOffice(officeDto));
    }

    @Operation(summary = "Remove car from office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Car not found",
                    content = @Content),})
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/removeCarFromOffice/{carId}")
    public ResponseEntity<?> removeCarFromOffice(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.removeCarFromOffice(carId));
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
