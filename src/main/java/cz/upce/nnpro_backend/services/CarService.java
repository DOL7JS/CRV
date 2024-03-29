package cz.upce.nnpro_backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.entities.*;
import cz.upce.nnpro_backend.repositories.*;
import cz.upce.nnpro_backend.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final SPZService spzService;
    private final SPZRepository spzRepository;
    private final SecurityService securityService;

    public CarService(CarRepository carRepository, OwnerRepository ownerRepository, CarOwnerRepository carOwnerRepository, SPZService spzService, SPZRepository spzRepository, SecurityService securityService) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.spzService = spzService;
        this.spzRepository = spzRepository;
        this.securityService = securityService;
    }

    public Car addCar(CarInDto carInDto) {
        if (carRepository.existsByVin(carInDto.getVin())) {
            throw new IllegalArgumentException("The car's vin already exists.");
        }
        Car car = ConversionService.convertToCar(carInDto);
        return carRepository.save(car);
    }

    public Car changeOwner(Long carId, Long idOwner) throws Exception {
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        CarOwner carOwnerOld = carOwnerRepository.findByCarIdAndEndOfSignUpIsNull(carId);
        if (carOwnerOld != null) {
            carOwnerOld.setEndOfSignUp(LocalDate.now());
            carOwnerRepository.save(carOwnerOld);
        }
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        if (car.getSPZ() == null || Objects.equals(car.getSPZ(), "")) {
            car.setSPZ(spzService.generateSPZ().getSPZ());
        }
        car.setBranchOffice(securityService.getAuthenticatedUser().getBranchOffice());
        car = carRepository.save(car);

        CarOwner carOwnerNew = new CarOwner();
        carOwnerNew.setCar(car);
        carOwnerNew.setOwner(owner);
        carOwnerNew.setStartOfSignUp(LocalDate.now());
        CarOwner save = carOwnerRepository.save(carOwnerNew);
        save.getCar().getCarOwners().add(save);
        return save.getCar();
    }

    public CarOutDto signOutCar(Long carId) {
        CarOwner carOwnerOld = carOwnerRepository.findByCarIdAndEndOfSignUpIsNull(carId);
        if (carOwnerOld == null) {
            throw new NoSuchElementException("Car not found!");
        }
        carOwnerOld.setEndOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwnerOld);
        spzRepository.save(new SPZ(carOwnerOld.getCar().getSPZ()));

        Car car = carOwnerOld.getCar();
        car.setBranchOffice(null);
        car = carRepository.save(car);
        carRepository.setSPZNullByCar(car);
        return ConversionService.convertToCarDetailOutDto(car);
    }

    public Car editCar(Long carId, CarInDto editCar) {
        if (carRepository.existsByVinAndIdIsNot(editCar.getVin(), carId)) {
            throw new IllegalArgumentException("The car's vin already exists.");
        }
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        Car editedCar = ConversionService.convertToCar(editCar, car);
        return carRepository.save(editedCar);
    }


    public Car signInCar(Long carId, OwnerInDto ownerInDto) throws Exception {
        CarOwner byCarIdAndEndOfSignUpIsNull = carOwnerRepository.findByCarIdAndEndOfSignUpIsNull(carId);
        if (byCarIdAndEndOfSignUpIsNull != null) {
            byCarIdAndEndOfSignUpIsNull.setEndOfSignUp(LocalDate.now());
            carOwnerRepository.save(byCarIdAndEndOfSignUpIsNull);
        }
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        if (car.getSPZ() == null || Objects.equals(car.getSPZ(), "")) {
            car.setSPZ(spzService.generateSPZ().getSPZ());
        }
        car.setBranchOffice(securityService.getAuthenticatedUser().getBranchOffice());

        car = carRepository.save(car);
        Owner owner = ConversionService.convertToOwner(ownerInDto);
        owner = ownerRepository.save(owner);
        CarOwner carOwner = new CarOwner();
        carOwner.setCar(car);
        carOwner.setOwner(owner);
        carOwner.setStartOfSignUp(LocalDate.now());
        CarOwner save = carOwnerRepository.save(carOwner);
        save.getCar().getCarOwners().add(carOwner);
        return save.getCar();
    }

    public CarOutDto getCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        return ConversionService.convertToCarDetailOutDto(car);
    }


    public List<CarOutDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return new ArrayList<>(cars.stream().map(ConversionService::convertToCarDetailOutDto).toList());
    }
}
