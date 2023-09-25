package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.*;
import cz.upce.nnpro_backend.dtos.CarDetailOutDto;
import cz.upce.nnpro_backend.dtos.CarIdOwnerIdDto;
import cz.upce.nnpro_backend.dtos.CarOfficeDto;
import cz.upce.nnpro_backend.dtos.CreateCarDto;
import cz.upce.nnpro_backend.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final BranchOfficeRepository branchOfficeRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final SPZService spzService;
    private final SPZRepository spzRepository;

    public CarService(CarRepository carRepository, OwnerRepository ownerRepository, BranchOfficeRepository branchOfficeRepository, CarOwnerRepository carOwnerRepository, SPZService spzService, SPZRepository spzRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.branchOfficeRepository = branchOfficeRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.spzService = spzService;
        this.spzRepository = spzRepository;
    }

    public Car addCar(CreateCarDto createCarDto) throws Exception {
        if (carRepository.existsByVin(createCarDto.getVin())) {
            throw new IllegalArgumentException("The car's vin already exists.");
        }
        Car car = ConversionService.convertToCar(createCarDto, spzService.generateSPZ().getSPZ());
        return carRepository.save(car);

    }

    public Owner changeOwner(Long carId, Long idOwner) {
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        CarOwner carOwnerOld = carOwnerRepository.findByCarIdAndEndOfSignUpIsNull(carId);
        carOwnerOld.setEndOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwnerOld);
        CarOwner carOwnerNew = new CarOwner();
        carOwnerNew.setCar(car);
        carOwnerNew.setOwner(owner);
        carOwnerNew.setStartOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwnerNew);
        return owner;
    }

    public void signOutCar(CarIdOwnerIdDto carIdOwnerIdDto) {
        CarOwner carOwnerOld = carOwnerRepository.findByCarIdAndOwnerId(carIdOwnerIdDto.getCarId(), carIdOwnerIdDto.getOwnerId());
        carOwnerOld.setEndOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwnerOld);
        carRepository.setSPZNullByCar(carOwnerOld.getCar());
        spzRepository.save(new SPZ(carOwnerOld.getCar().getSPZ()));
//        carOwnerRepository.deleteByCarIdAndOwnerId(carIdOwnerIdDto.getCarId(), carIdOwnerIdDto.getOwnerId());
//        Car deletedCar = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
//        carRepository.deleteById(carId);
//        return deletedCar;
    }

    public Car putCarInDeposit(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        car.setInDeposit(true);
        carRepository.save(car);
        return car;
    }

    public Car editCar(Long carId, CreateCarDto editCar) {
        if (carRepository.existsByVinAndIdIsNot(editCar.getVin(), carId)) {
            throw new IllegalArgumentException("The car's vin already exists.");
        }
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        Car editedCar = ConversionService.convertToCar(editCar, car);
        return carRepository.save(editedCar);
    }

    public Owner signInCar(CreateCarDto createCarDto, Long ownerId) throws Exception {
        Car car = addCar(createCarDto);
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        CarOwner carOwner = new CarOwner();
        carOwner.setCar(car);
        carOwner.setOwner(owner);
        carOwner.setStartOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwner);
        return owner;
    }

    public Owner signInExistingCar(CarIdOwnerIdDto carIdOwnerIdDto) throws Exception {
        Car car = carRepository.findById(carIdOwnerIdDto.getCarId()).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        if (carOwnerRepository.existsByCarAndEndOfSignUpIsNull(car)) {//je prihlasene?
            throw new IllegalArgumentException("The car is still sign up.");
        }
        Owner owner = ownerRepository.findById(carIdOwnerIdDto.getOwnerId()).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        CarOwner carOwner = new CarOwner();
        if (car.getSPZ() == null) {
            car.setSPZ(spzService.generateSPZ().getSPZ());
        }
        carOwner.setCar(car);
        carOwner.setOwner(owner);
        carOwner.setStartOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwner);
        return owner;
    }

    public Car addCarToOffice(CarOfficeDto officeDto) {
        Car car = carRepository.findById(officeDto.getCarId()).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        BranchOffice branchOffice = branchOfficeRepository.findById(officeDto.getOfficeId()).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
        car.setBranchOffice(branchOffice);
        return carRepository.save(car);
    }

    public Car removeCarFromOffice(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        car.setBranchOffice(null);
        return carRepository.save(car);
    }

    public CarDetailOutDto getCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        return ConversionService.convertToCarDetailOutDto(car);
    }


    public CarDetailOutDto getCarByVin(String vin) {
        Car car = carRepository.findByVin(vin).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        return ConversionService.convertToCarDetailOutDto(car);
    }

    public CarDetailOutDto getCarBySPZ(String spz) {
        Car car = carRepository.findBySPZ(spz).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        return ConversionService.convertToCarDetailOutDto(car);
    }

    public List<CarDetailOutDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarDetailOutDto> carDetailOutDtos = new ArrayList<>();
        for (Car car : cars) {
            carDetailOutDtos.add(ConversionService.convertToCarDetailOutDto(car));
        }
        return carDetailOutDtos;
    }
}
