package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.repositories.BranchOfficeRepository;
import cz.upce.nnpro_backend.repositories.CarRepository;
import cz.upce.nnpro_backend.security.SecurityService;
import cz.upce.nnpro_backend.security.UserDetailDto;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class CarServiceTest {

    @MockBean
    private SecurityService securityService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @AfterEach
    @BeforeEach
    void clearData() {
        carRepository.deleteAll();
    }

    @Test
    void addCarTest() {
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
        assertEquals(carDto.getManufacturer(), car.getManufacturer());
        assertEquals(carDto.getType(), car.getType());
    }

    @Test
    void editCarTest() {
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

        CarInDto editCarDto = new CarInDto();
        editCarDto.setColor("New_Color");
        editCarDto.setEnginePower(150);
        editCarDto.setTorque(150);
        editCarDto.setType("Type");
        editCarDto.setVin("4d5s4d5s4d5sd4s54s5d4");
        editCarDto.setEmissionStandard(150);
        editCarDto.setInDeposit(false);
        editCarDto.setManufacturer("Manufacturer");
        editCarDto.setYearOfCreation(LocalDate.now());

        Car editedCar = carService.editCar(car.getId(), editCarDto);

        assertNotNull(editedCar);
        assertEquals(editCarDto.getColor(), editedCar.getColor());
    }

    @Test
    void changeOwnerTest() throws Exception {
        BranchOffice office = new BranchOffice("district", "region", "city");
        BranchOffice savedOffice = branchOfficeRepository.save(office);
        when(securityService.getAuthenticatedUser()).thenReturn(new UserDetailDto("username", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE")), 1L, savedOffice));

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
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setStreet("Street");
        ownerInDto.setZipCode(12345);
        ownerInDto.setCity("City");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setLastName("LastName");
        ownerInDto.setEmail("email@email.com");
        Owner saveOwner = ownerService.addOwner(ownerInDto);
        Car signedCar = carService.signInCar(car.getId(), ownerInDto);

        Car changeOwnerCar = carService.changeOwner(signedCar.getId(), saveOwner.getId());
        assertEquals(2, changeOwnerCar.getCarOwners().size());
        assertTrue(changeOwnerCar.getCarOwners().stream().anyMatch(item -> Objects.equals(item.getCar().getId(), signedCar.getId())));
        assertTrue(changeOwnerCar.getCarOwners().stream().anyMatch(item -> Objects.equals(item.getOwner().getId(), saveOwner.getId())));

    }

    @Test
    void signOutCarTest() throws Exception {
        BranchOffice office = new BranchOffice("district", "region", "city");
        BranchOffice savedOffice = branchOfficeRepository.save(office);
        when(securityService.getAuthenticatedUser()).thenReturn(new UserDetailDto("username", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE")), 1L, savedOffice));

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
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setStreet("Street");
        ownerInDto.setZipCode(12345);
        ownerInDto.setCity("City");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setLastName("LastName");
        ownerInDto.setEmail("email@email.com");
        Car signedCar = carService.signInCar(car.getId(), ownerInDto);

        carService.signOutCar(signedCar.getId());

        Optional<Car> byId = carRepository.findById(signedCar.getId());
        assertEquals(1, byId.get().getCarOwners().size());
        assertTrue(byId.get().getCarOwners().stream().anyMatch(item -> item.getEndOfSignUp() != null));
    }


    @Test
    void signInCarTest() throws Exception {
        BranchOffice office = new BranchOffice("district", "region", "city");
        BranchOffice savedOffice = branchOfficeRepository.save(office);
        when(securityService.getAuthenticatedUser()).thenReturn(new UserDetailDto("username", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE")), 1L, savedOffice));

        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setStreet("Street");
        ownerInDto.setCity("City");
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setLastName("LastName");
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setEmail("email@email.com");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setZipCode(12345);

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
        Car car = carService.addCar(carDto);

        Car signedCar = carService.signInCar(car.getId(), ownerInDto);

        assertNotNull(signedCar);
        assertEquals(1, signedCar.getCarOwners().size());
        assertTrue(signedCar.getCarOwners().stream().anyMatch(item -> item.getCar().getId().equals(signedCar.getId())));
    }

    @Test
    void getCarTest() {
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
        Car saveCar = carService.addCar(carDto);

        CarOutDto car = carService.getCar(saveCar.getId());

        assertNotNull(car);
        assertEquals(saveCar.getId(), car.getId());
    }


    @Test
    void getAllCarsTest() {
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

        CarInDto carDto2 = new CarInDto();
        carDto2.setColor("New_Color");
        carDto2.setEnginePower(150);
        carDto2.setTorque(150);
        carDto2.setType("Type");
        carDto2.setVin("4d5s4d5s4d5sd4s54s5d4");
        carDto2.setEmissionStandard(150);
        carDto2.setInDeposit(false);
        carDto2.setManufacturer("Manufacturer");
        carDto2.setYearOfCreation(LocalDate.now());
        Car car1 = carService.addCar(carDto);
        Car car2 = carService.addCar(carDto2);

        List<CarOutDto> allCars = carService.getAllCars();

        assertEquals(2, allCars.size());
        assertEquals(car1.getId(), allCars.get(0).getId());
        assertEquals(car2.getId(), allCars.get(1).getId());
    }

}
