package cz.upce.nnpro_backend.controllers;


import cz.upce.nnpro_backend.dtos.CarIdOwnerIdDto;
import cz.upce.nnpro_backend.dtos.CarOfficeDto;
import cz.upce.nnpro_backend.dtos.CreateCarDto;
import cz.upce.nnpro_backend.services.CarService;
import cz.upce.nnpro_backend.services.SPZService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
@CrossOrigin
public class CarController {
    private final CarService carService;
    private final SPZService spzService;

    public CarController(CarService carService, SPZService spzService) {
        this.carService = carService;
        this.spzService = spzService;
    }

    @GetMapping("/getCar/{carId}")
    public ResponseEntity<?> getCar(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCar(carId));
    }

    @GetMapping("/generateSPZ")
    public ResponseEntity<?> gener() throws Exception {
        return ResponseEntity.ok(spzService.generateSPZ());
    }

    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(@RequestBody CreateCarDto createCarDto) throws Exception {
        return ResponseEntity.ok(carService.addCar(createCarDto));
    }

    @PostMapping("/signInCar/{ownerId}")
    public ResponseEntity<?> signInCar(@RequestBody CreateCarDto createCarDto, @PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(carService.signInCar(createCarDto, ownerId));
    }

    @PostMapping("/signInCar/")
    public ResponseEntity<?> signInExistingCar(@RequestBody CarIdOwnerIdDto createCarDto) throws Exception {
        return ResponseEntity.ok(carService.signInExistingCar(createCarDto));
    }

    @PutMapping("/changeOwner")
    public ResponseEntity<?> changeOwner(@RequestBody CarIdOwnerIdDto carIdOwnerIdDto) {
        return ResponseEntity.ok(carService.changeOwner(carIdOwnerIdDto.getCarId(), carIdOwnerIdDto.getOwnerId()));
    }

    @DeleteMapping("/signOutCar")
    public void deleteCar(@RequestBody CarIdOwnerIdDto carIdOwnerIdDto) {
        carService.signOutCar(carIdOwnerIdDto);
    }

    @PutMapping("/putToDeposit/{carId}")
    public ResponseEntity<?> putCarToDeposit(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.putCarInDeposit(carId));
    }

    @PutMapping({"/addCarToOffice", "editCarInOffice"})
    public ResponseEntity<?> addCarToOffice(@RequestBody CarOfficeDto officeDto) {
        return ResponseEntity.ok(carService.addCarToOffice(officeDto));
    }

    @PutMapping("/removeCarFromOffice/{carId}")
    public ResponseEntity<?> removeCarFromOffice(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.removeCarFromOffice(carId));
    }

    @PutMapping("/editCar/{carId}")
    public ResponseEntity<?> editCar(@PathVariable Long carId, @RequestBody CreateCarDto editCar) {
        return ResponseEntity.ok(carService.editCar(carId, editCar));
    }


}
