package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.BranchOffice;
import cz.upce.nnpro_backend.Entities.Car;
import cz.upce.nnpro_backend.Entities.Owner;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.repositories.BranchOfficeRepository;
import cz.upce.nnpro_backend.repositories.CarRepository;
import cz.upce.nnpro_backend.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final BranchOfficeRepository branchOfficeRepository;

    public CarService(CarRepository carRepository, OwnerRepository ownerRepository, BranchOfficeRepository branchOfficeRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.branchOfficeRepository = branchOfficeRepository;
    }

    public Car addCar(CreateCarDto createCarDto) {
        Car car = ConversionService.convertToCar(createCarDto);
        Car save = carRepository.save(car);
        return save;

    }

    public Owner changeOwner(Long carId, Long idOwner) {
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        car.setOwner(owner);
        carRepository.save(car);
        owner.addCar(car);
        ownerRepository.save(owner);
        return owner;
    }

    public Car signOutCar(Long carId) {
        Car deletedCar = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        carRepository.deleteById(carId);
        return deletedCar;
    }

    public Car putCarInDeposit(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        car.setInDeposit(true);
        carRepository.save(car);
        return car;
    }

    public Car editCar(Long carId, CreateCarDto editCar) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        Car editedCar = ConversionService.convertToCar(editCar, car);
        Car save = carRepository.save(editedCar);
        return save;
    }

    public Owner signInCar(CreateCarDto createCarDto, Long ownerId) {
        Car car = addCar(createCarDto);
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        owner.addCar(car);
        car.setOwner(owner);
        return owner;
    }

    public Car addCarToOffice(CarOfficeDto officeDto) {
        Car car = carRepository.findById(officeDto.getCarId()).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        BranchOffice branchOffice = branchOfficeRepository.findById(officeDto.getOfficeId()).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
        car.setBranchOffice(branchOffice);
        Car save = carRepository.save(car);
        return save;
    }

    public CarDetailOutDto getCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        CarDetailOutDto carDetailOutDto = ConversionService.convertToCarDetailOutDto(car);
        return carDetailOutDto;
    }


}
