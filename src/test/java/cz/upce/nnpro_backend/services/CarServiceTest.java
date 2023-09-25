package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.dtos.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private CarService carService;

    @Test
    @Order(1)
    void addCarTest() throws Exception {
        CarInDto carDto = new CarInDto();

        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("d4dad4a54d5sd");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        Car car = carService.addCar(carDto);
        assertNotNull(car);
    }

    @Test
    void changeOwnerTest() {
        //Owner owner = carService.changeOwner(1L, 5L);
    }

    @Test
    void signOutCarTest() {
        //CarIdOwnerIdDto carIdOwnerIdDto = new CarIdOwnerIdDto();
        //carIdOwnerIdDto.setCarId(1L);
        //carIdOwnerIdDto.setOwnerId(5L);
        //carService.signOutCar(carIdOwnerIdDto);
    }

    @Test
    void putCarInDepositTest() {
        Car car = carService.putCarInDeposit(1L);
        assertTrue(car.isInDeposit());
    }

    @Test
    void editCarTest() {
        CarInDto carDto = new CarInDto();
        carDto.setColor("New_Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("4d5s4d5s4d5sd4s54s5d4");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        Car car = carService.editCar(1L, carDto);
        assertNotNull(car);
    }

    @Test
    void signInCarTest() throws Exception {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setStreet("Street");
        ownerInDto.setCity("City");
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setLastName("LastName");
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setZipCode(12345);

        ownerService.addOwner(ownerInDto);

        CarInDto carDto = new CarInDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("das4da4545d4asd");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        Owner owner = carService.signInCar(carDto, 1L);
        assertNotNull(owner);
    }

    @Test
    void signInExistingCarTest() throws Exception {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setStreet("Street");
        ownerInDto.setCity("City");
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setLastName("LastName");
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setZipCode(12345);

        ownerService.addOwner(ownerInDto);

        CarOwnerDto car = new CarOwnerDto();
        car.setOwnerId(1L);
        car.setCarId(1L);
        Owner owner = carService.signInExistingCar(car);
        assertNotNull(owner);
    }

    @Test
    void addCarToOfficeTest() {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region");
        branchOfficeInDto.setDistrict("Disctrict");
        branchOfficeInDto.setCity("City");
        BranchOffice saveOffice = branchOfficeService.addOffice(branchOfficeInDto);

        CarBranchOfficeDto carDto = new CarBranchOfficeDto();

        carDto.setCarId(1L);
        carDto.setOfficeId(saveOffice.getId());

        Car car = carService.addCarToOffice(carDto);
        assertEquals(1L, car.getBranchOffice().getId());
    }

    @Test
    void getCarTest() throws Exception {
        CarInDto carDto = new CarInDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("das4da4545d4asd");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());
        //Car saveCar = carService.addCar(carDto);

        //CarDetailOutDto car = carService.getCar(saveCar.getId());
        //assertNotNull(car);
    }

    @Test
    void getCarByVin() {
        //CarDetailOutDto car = carService.getCarByVin("vin");
        //assertNotNull(car);
    }

    @Test
    void getCarBySPZTest() {
        //carService.getCarBySPZ("1E1 1117");
    }

    @Test
    void getAllCarsTest() {
        //carService.getAllCars();
    }

    @Test
    @Order(13)
    void removeCarFromOfficeTest() {
        carService.removeCarFromOffice(1L);
    }
}
