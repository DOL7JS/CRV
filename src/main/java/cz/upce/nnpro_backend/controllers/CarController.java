package cz.upce.nnpro_backend.controllers;


import cz.upce.nnpro_backend.dtos.CarIdOwnerIdDto;
import cz.upce.nnpro_backend.dtos.CarOfficeDto;
import cz.upce.nnpro_backend.dtos.CreateCarDto;
import cz.upce.nnpro_backend.services.CarService;
import cz.upce.nnpro_backend.services.SPZService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
@CrossOrigin
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getCar/{carId}")
    public ResponseEntity<?> getCar(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCar(carId));
    }

    @GetMapping("/getCarByVin/{vin}")//pripraveno pro STK
    public ResponseEntity<?> getCarByVin(@PathVariable String vin) {
        return ResponseEntity.ok(carService.getCarByVin(vin));
    }

    @GetMapping("/getCarByVin/{spz}")//pripraveno pro STK
    public ResponseEntity<?> getCarBySPZ(@PathVariable String spz) {
        return ResponseEntity.ok(carService.getCarBySPZ(spz));
    }

    @GetMapping("/isCarStolenByVin/{vin}")
    public ResponseEntity<?> isCarStolenByVin(@PathVariable String vin) {
        return ResponseEntity.ok(carService.isCarStolenByVin(vin));
    }

    @GetMapping("/isCarStolenBySpz/{spz}")
    public ResponseEntity<?> isCarStolenBySpz(@PathVariable String spz) {
        return ResponseEntity.ok(carService.isCarStolenBySpz(spz));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(@RequestBody CreateCarDto createCarDto) throws Exception {
        return ResponseEntity.ok(carService.addCar(createCarDto));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/signInCar/{ownerId}")
    public ResponseEntity<?> signInCar(@RequestBody CreateCarDto createCarDto, @PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(carService.signInCar(createCarDto, ownerId));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/signInCar/")
    public ResponseEntity<?> signInExistingCar(@RequestBody CarIdOwnerIdDto createCarDto) throws Exception {
        return ResponseEntity.ok(carService.signInExistingCar(createCarDto));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/changeOwner")
    public ResponseEntity<?> changeOwner(@RequestBody CarIdOwnerIdDto carIdOwnerIdDto) {
        return ResponseEntity.ok(carService.changeOwner(carIdOwnerIdDto.getCarId(), carIdOwnerIdDto.getOwnerId()));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @DeleteMapping("/signOutCar")
    public void deleteCar(@RequestBody CarIdOwnerIdDto carIdOwnerIdDto) {
        carService.signOutCar(carIdOwnerIdDto);
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/putToDeposit/{carId}")
    public ResponseEntity<?> putCarToDeposit(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.putCarInDeposit(carId));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping({"/addCarToOffice", "editCarInOffice"})
    public ResponseEntity<?> addCarToOffice(@RequestBody CarOfficeDto officeDto) {
        return ResponseEntity.ok(carService.addCarToOffice(officeDto));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/removeCarFromOffice/{carId}")
    public ResponseEntity<?> removeCarFromOffice(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.removeCarFromOffice(carId));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/editCar/{carId}")
    public ResponseEntity<?> editCar(@PathVariable Long carId, @RequestBody CreateCarDto editCar) {
        return ResponseEntity.ok(carService.editCar(carId, editCar));
    }


}
