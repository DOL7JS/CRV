package cz.upce.nnpro_backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.entities.*;
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

    public Car addCar(CarInDto carInDto) throws Exception {
        if (carRepository.existsByVin(carInDto.getVin())) {
            throw new IllegalArgumentException("The car's vin already exists.");
        }
        Car car = ConversionService.convertToCar(carInDto, spzService.generateSPZ().getSPZ());
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
        if (car.getSPZ() == null) {
            car.setSPZ(spzService.generateSPZ().getSPZ());
            car = carRepository.save(car);
        }
        CarOwner carOwnerNew = new CarOwner();
        carOwnerNew.setCar(car);
        carOwnerNew.setOwner(owner);
        carOwnerNew.setStartOfSignUp(LocalDate.now());
        CarOwner save = carOwnerRepository.save(carOwnerNew);
        save.getCar().getCarOwners().add(save);
        return save.getCar();
    }

    public void signOutCar(CarOwnerDto carOwnerDto) {
        CarOwner carOwnerOld = carOwnerRepository.findByCarIdAndOwnerId(carOwnerDto.getCarId(), carOwnerDto.getOwnerId());
        carOwnerOld.setEndOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwnerOld);
        carRepository.setSPZNullByCar(carOwnerOld.getCar());
        spzRepository.save(new SPZ(carOwnerOld.getCar().getSPZ()));
    }

    public CarOutDto signOutCar(Long carId) {
        CarOwner carOwnerOld = carOwnerRepository.findByCarIdAndEndOfSignUpIsNull(carId);
        if (carOwnerOld != null) {
            carOwnerOld.setEndOfSignUp(LocalDate.now());
            carOwnerRepository.save(carOwnerOld);
            carRepository.setSPZNullByCar(carOwnerOld.getCar());
            spzRepository.save(new SPZ(carOwnerOld.getCar().getSPZ()));
            return ConversionService.convertToCarDetailOutDto(carOwnerOld.getCar());
        }
        return null;
    }

    public Car putCarInDeposit(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        car.setInDeposit(true);
        carRepository.save(car);
        return car;
    }

    public Car editCar(Long carId, CarInDto editCar) {
        if (carRepository.existsByVinAndIdIsNot(editCar.getVin(), carId)) {
            throw new IllegalArgumentException("The car's vin already exists.");
        }
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        Car editedCar = ConversionService.convertToCar(editCar, car);
        return carRepository.save(editedCar);
    }

    public Owner signInCar(CarInDto carInDto, Long ownerId) throws Exception {
        Car car = addCar(carInDto);
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        CarOwner carOwner = new CarOwner();
        carOwner.setCar(car);
        carOwner.setOwner(owner);
        carOwner.setStartOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwner);
        return owner;
    }

    public Car signInCar(Long carId, OwnerInDto ownerInDto) throws Exception {
        CarOwner byCarIdAndEndOfSignUpIsNull = carOwnerRepository.findByCarIdAndEndOfSignUpIsNull(carId);
        if (byCarIdAndEndOfSignUpIsNull != null) {
            byCarIdAndEndOfSignUpIsNull.setEndOfSignUp(LocalDate.now());
            carOwnerRepository.save(byCarIdAndEndOfSignUpIsNull);
        }
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        if (car.getSPZ() == null) {
            car.setSPZ(spzService.generateSPZ().getSPZ());
        }
        car = carRepository.save(car);
        Owner owner = ConversionService.convertToOwner(ownerInDto);
        owner = ownerRepository.save(owner);
        CarOwner carOwner = new CarOwner();
        carOwner.setCar(car);
        carOwner.setOwner(owner);
        carOwner.setStartOfSignUp(LocalDate.now());
        carOwnerRepository.save(carOwner);
        return car;
    }

    public Owner signInExistingCar(CarOwnerDto carOwnerDto) throws Exception {
        Car car = carRepository.findById(carOwnerDto.getCarId()).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        if (carOwnerRepository.existsByCarAndEndOfSignUpIsNull(car)) {//je prihlasene?
            throw new IllegalArgumentException("The car is still sign up.");
        }
        Owner owner = ownerRepository.findById(carOwnerDto.getOwnerId()).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
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

    public Car addCarToOffice(CarBranchOfficeDto officeDto) {
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

    public CarOutDto getCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        return ConversionService.convertToCarDetailOutDto(car);
    }


    public CarOutDto getCarByVin(String vin) {
        Car car = carRepository.findByVin(vin).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        return ConversionService.convertToCarDetailOutDto(car);
    }

    public CarOutDto getCarBySPZ(String spz) {
        Car car = carRepository.findBySPZ(spz).orElseThrow(() -> new NoSuchElementException("Car not found!"));
        return ConversionService.convertToCarDetailOutDto(car);
    }

    public List<CarOutDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return new ArrayList<>(cars.stream().map(ConversionService::convertToCarDetailOutDto).toList());
    }

    public String exportData() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        List<Car> cars = carRepository.findAll();
        List<Owner> owners = ownerRepository.findAll();
        String jsonCars = mapper.writeValueAsString(cars);
        String jsonOwners = mapper.writeValueAsString(owners);

        return "{ \"cars\": " + jsonCars + ",\"owners\":" + jsonOwners + "}";
    }

    public void importData(List<Car> cars, List<Owner> owners) {
        cars.stream().filter(car -> !carRepository.existsByVin(car.getVin()) && !carRepository.existsBySPZ(car.getSPZ())).forEach(car -> car.setId(null));
        owners.forEach(owner -> owner.setId(null));
        carRepository.saveAll(cars);
        ownerRepository.saveAll(owners);
    }
}
