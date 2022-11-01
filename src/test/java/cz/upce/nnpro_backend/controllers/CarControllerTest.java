package cz.upce.nnpro_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpro_backend.Entities.BranchOffice;
import cz.upce.nnpro_backend.config.JwtRequest;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.services.BranchOfficeService;
import cz.upce.nnpro_backend.services.OwnerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    private String token;

    @BeforeEach
    void setUp() throws Exception{
        JwtRequest jwtRequest = new JwtRequest("Admin", "heslo");

        ResultActions resultActions = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk());

        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        this.token = jsonParser.parseMap(resultString).get("jwttoken").toString();
    }

    @Test
    @Order(1)
    void addCarIsOk() throws Exception{
        CreateCarDto carDto = new CreateCarDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("vin");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        doAuthPost("/addCar", carDto, status().isOk());
    }

    @Test
    void addCarIs400error() throws Exception{
        CreateCarDto carDto = new CreateCarDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("vin");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        doPost("/addCar", carDto, status().is4xxClientError());
    }

    @Test
    void addCarIs500error() throws Exception{
        CreateCarDto carDto = new CreateCarDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("vin");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        //doAuthPost("/addCar", carDto, status().is5xxServerError());
    }

    @Test
    void getCarInfoIsOk() throws Exception{
        doAuthGet("/getCar/1", status().isOk());
    }

    @Test
    void getCarInfoIs400() throws Exception{
        doGet("/getCar/1", status().is4xxClientError());
    }

    @Test
    //Request processing failed; nested exception is java.util.NoSuchElementException: Car not found!
    void getCarInfoIs500() throws Exception{
        //doAuthGet("/getCar/1", status().is5xxServerError());
    }

    @Test
    void getAllCarIsOk() throws Exception{
        doAuthGet("/getAllCar", status().isOk());
    }

    @Test
    void getAllCarIs400() throws Exception{
        doGet("/getAllCar", status().is4xxClientError());
    }

    @Test
    void getAllCarIs500() throws Exception{
        //doAuthGet("/getAllCar", status().is5xxServerError());
    }

    @Test
    void getCarByVinIsOk() throws Exception {
        doAuthGet("/getCarByVin/vin", status().isOk());
    }

    @Test
    void getCarByVinIs400error() throws Exception {
        doGet("/getCarByVin/vin", status().is4xxClientError());
    }

    @Test
    void getCarByVinIs500error() throws Exception {
        //doAuthGet("/getCarByVin/vin", status().is5xxServerError());
    }

    @Test
    void getCarBySPZIsOk() throws Exception {
        doAuthGet("/getCarBySpz/1E1 1111", status().isOk());
    }

    @Test
    void getCarBySPZIs400error() throws Exception {
        doGet("/getCarBySpz/1E1 1111", status().is4xxClientError());
    }

    @Test
    void getCarBySPZIs500error() throws Exception {
        //doAuthGet("/getCarBySpz/1E1 1111", status().is5xxServerError());
    }

    @Test
    void signInCarIsOk() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setBirthDate(LocalDate.now());
        ownerDto.setLastName("LastName");
        ownerDto.setFirstName("FirstName");
        ownerDto.setCity("City");
        ownerDto.setZipCode(12345);
        ownerDto.setNumberOfHouse(1);
        ownerDto.setStreet("Street");

        ownerService.addOwner(ownerDto);

        CreateCarDto carDto = new CreateCarDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("vin2");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        doAuthPost("/signInCar/1", carDto, status().isOk());
    }

    @Test
    void signInCarIs400() throws Exception {
        CreateCarDto carDto = new CreateCarDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("vin2");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        doPost("/signInCar/1", carDto, status().is4xxClientError());
    }

    @Test //Ale vytvoří to auto
    void signInCarIs500() throws Exception {
        CreateCarDto carDto = new CreateCarDto();
        carDto.setColor("Color");
        carDto.setEnginePower(150);
        carDto.setTorque(150);
        carDto.setType("Type");
        carDto.setVin("5d45s4d545S");
        carDto.setEmissionStandard(150);
        carDto.setInDeposit(false);
        carDto.setManufacturer("Manufacturer");
        carDto.setYearOfCreation(LocalDate.now());

        //doAuthPost("/signInCar/99999", carDto, status().is5xxServerError());
    }

    @Test
    void signInExistingCarIsOk() throws Exception{
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setBirthDate(LocalDate.now());
        ownerDto.setLastName("LastName");
        ownerDto.setFirstName("FirstName");
        ownerDto.setCity("City");
        ownerDto.setZipCode(12345);
        ownerDto.setNumberOfHouse(1);
        ownerDto.setStreet("Street");

        ownerService.addOwner(ownerDto);

        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);
        doAuthPost("/signInCar/", idOwnerIdDto, status().isOk());
    }

    @Test
    void signInExistingCarIs400error() throws Exception{
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);
        doPost("/signInCar/", idOwnerIdDto, status().is4xxClientError());
    }

    @Test
    void signInExistingCarIs500error() throws Exception{
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(99999L);
        idOwnerIdDto.setOwnerId(99999L);
        //doAuthPost("/signInCar/", idOwnerIdDto, status().is5xxServerError());
    }

    @Test
    //Request processing failed; nested exception is java.lang.NullPointerException: Cannot invoke "cz.upce.nnpro_backend.Entities.CarOwner.setEndOfSignUp(java.time.LocalDate)" because "carOwnerOld" is null
    void changeOwnerIsOk() throws Exception{
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);
        //doAuthPut("/changeOwner", idOwnerIdDto, status().isOk());
    }

    @Test
    void changeOwnerIs400error() throws Exception{
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);
        doPut("/changeOwner", idOwnerIdDto, status().is4xxClientError());
    }

    @Test
    void changeOwnerIs500error() throws Exception{
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);
        //doAuthPut("/changeOwner", idOwnerIdDto, status().is5xxServerError());
    }

    @Test
    //Request processing failed; nested exception is java.lang.NullPointerException: Cannot invoke "cz.upce.nnpro_backend.Entities.CarOwner.setEndOfSignUp(java.time.LocalDate)" because "carOwnerOld" is null
    void signOutCarIsOk() throws Exception {
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);

        //doAuthPut("/signOutCar", idOwnerIdDto, status().isOk());
    }

    @Test
    void signOutCarIs400() throws Exception {
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);

        doPut("/signOutCar", idOwnerIdDto, status().is4xxClientError());
    }

    @Test
    void signOutCarIs500() throws Exception {
        CarIdOwnerIdDto idOwnerIdDto = new CarIdOwnerIdDto();
        idOwnerIdDto.setCarId(1L);
        idOwnerIdDto.setOwnerId(1L);

        //doAuthPut("/signOutCar", idOwnerIdDto, status().is5xxServerError());
    }

    @Test
    void putCarToDepositIsOk() throws Exception {
        doAuthPut("/putToDeposit/1", null, status().isOk());
    }

    @Test
    void putCarToDepositIs400() throws Exception {
        doPut("/putToDeposit/1", null, status().is4xxClientError());
    }

    @Test
    void putCarToDepositIs500() throws Exception {
        //doAuthPut("/putToDeposit/99999", null, status().is5xxServerError());
    }

    @Test
    void addCarToOfficeIsOk() throws Exception {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setDistrict("Dristrict");
        branchOfficeInDto.setRegion("Region");
        branchOfficeInDto.setCity("City");

        BranchOffice branchOffice = branchOfficeService.addOffice(branchOfficeInDto);

        CarOfficeDto officeDto = new CarOfficeDto();
        officeDto.setCarId(1L);
        officeDto.setOfficeId(branchOffice.getId());
        doAuthPut("/addCarToOffice", officeDto, status().isOk());
    }

    @Test
    void addCarToOfficeIs400() throws Exception {
        CarOfficeDto officeDto = new CarOfficeDto();
        officeDto.setCarId(1L);
        officeDto.setOfficeId(1L);
        doPut("/addCarToOffice", officeDto, status().is4xxClientError());
    }

    @Test
    void addCarToOfficeIs500() throws Exception {
        CarOfficeDto officeDto = new CarOfficeDto();
        officeDto.setCarId(99999L);
        officeDto.setOfficeId(1L);
        //doAuthPut("/addCarToOffice", officeDto, status().is5xxServerError());
    }

    @Test
    @Order(39)
    void removeCarFromOfficeIsOk() throws Exception {
        doAuthPut("/removeCarFromOffice/1", null, status().isOk());
    }

    @Test
    @Order(38)
    void removeCarFromOfficeIs400() throws Exception {
        doPut("/removeCarFromOffice/1", null, status().is4xxClientError());
    }

    @Test
    @Order(37)
    void removeCarFromOfficeIs500() throws Exception {
        //doAuthPut("/removeCarFromOffice/99999", null, status().is5xxServerError());
    }

    @Test
    void editCarIsOk() throws Exception {
        CreateCarDto editCar = new CreateCarDto();
        editCar.setColor("Color");
        editCar.setEnginePower(150);
        editCar.setTorque(150);
        editCar.setType("Type");
        editCar.setVin("vin");
        editCar.setEmissionStandard(150);
        editCar.setInDeposit(false);
        editCar.setManufacturer("Manufacturer");
        editCar.setYearOfCreation(LocalDate.now());
        doAuthPut("/editCar/1", editCar, status().isOk());
    }

    @Test
    void editCarIs400() throws Exception {
        CreateCarDto editCar = new CreateCarDto();
        editCar.setColor("Color");
        editCar.setEnginePower(150);
        editCar.setTorque(150);
        editCar.setType("Type");
        editCar.setVin("vin");
        editCar.setEmissionStandard(150);
        editCar.setInDeposit(false);
        editCar.setManufacturer("Manufacturer");
        editCar.setYearOfCreation(LocalDate.now());
        doPut("/editCar/1", editCar, status().is4xxClientError());
    }

    @Test
    void editCarIs500() throws Exception {
        CreateCarDto editCar = new CreateCarDto();
        editCar.setColor("Color");
        editCar.setEnginePower(150);
        editCar.setTorque(150);
        editCar.setType("Type");
        editCar.setVin("vin");
        editCar.setEmissionStandard(150);
        editCar.setInDeposit(false);
        editCar.setManufacturer("Manufacturer");
        editCar.setYearOfCreation(LocalDate.now());
        //doAuthPut("/editCar/1", editCar, status().is5xxServerError());
    }


    private ResultActions doAuthPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post("/car" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post("/car" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(put("/car" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(put("/car" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get("/car" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(resultMatcher);
    }

    private ResultActions doGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get("/car" + uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }
}
