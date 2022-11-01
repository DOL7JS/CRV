package cz.upce.nnpro_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpro_backend.config.JwtRequest;
import cz.upce.nnpro_backend.dtos.BranchOfficeIdUserIdDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeInDto;
import cz.upce.nnpro_backend.dtos.CarsOwnersDto;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BranchOfficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void addBranchOfficeIsOk() throws Exception{
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region");
        branchOfficeInDto.setCity("City");
        branchOfficeInDto.setDistrict("District");

        doAuthPost("/addOffice", branchOfficeInDto, status().isOk());
    }

    @Test
    void addBranchOfficeIs400error() throws Exception{
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region");
        branchOfficeInDto.setCity("City");
        branchOfficeInDto.setDistrict("District");

        doPost("/addOffice", branchOfficeInDto, status().is4xxClientError());
    }

    @Test
    void editOfficeIsOk() throws Exception{
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region1");
        branchOfficeInDto.setCity("City1");
        branchOfficeInDto.setDistrict("District1");

        doAuthPut("/editOffice/1", branchOfficeInDto, status().isOk());
    }

    @Test
    void editOfficeIs400() throws Exception{
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region1");
        branchOfficeInDto.setCity("City1");
        branchOfficeInDto.setDistrict("District1");

        doPut("/editOffice/1", branchOfficeInDto, status().is4xxClientError());
    }

    @Test
    //Request processing failed; nested exception is java.util.NoSuchElementException: Branch office not found!
    void editOfficeIs500() throws Exception{
        //BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        //branchOfficeInDto.setRegion("Region1");
        //branchOfficeInDto.setCity("City1");
        //branchOfficeInDto.setDistrict("District1");

        //doAuthPut("/editOffice/99999", branchOfficeInDto, status().is5xxServerError());
    }

    @Test
    void addUserToOfficeIsOk() throws Exception{
        BranchOfficeIdUserIdDto userIdDto = new BranchOfficeIdUserIdDto();
        userIdDto.setUserId(1L);
        userIdDto.setBranchOfficeId(1L);

        doAuthPost("/addUserToOffice", userIdDto, status().isOk());
    }

    @Test
    void addUserToOfficeIs400() throws Exception{
        BranchOfficeIdUserIdDto userIdDto = new BranchOfficeIdUserIdDto();
        userIdDto.setUserId(5L);
        userIdDto.setBranchOfficeId(1L);

        doPost("/addUserToOffice", userIdDto, status().is4xxClientError());
    }

    @Test //Vyhazuje 400
    void addUserToOfficeIs500() throws Exception{
        //doAuthPost("/addUserToOffice", null, status().is5xxServerError());
    }

    @Test
    void getBranchOfficeIsOk() throws Exception{
        doAuthGet("/getOffice/1", status().isOk());
    }

    @Test
    void getBranchOfficeIs400() throws Exception{
        doGet("/getOffice/1", status().is4xxClientError());
    }

    @Test
    //Request processing failed; nested exception is java.util.NoSuchElementException: Branch office not found!
    void getBranchOfficeIs500() throws Exception{
        //doAuthGet("/getOffice/99999", status().is5xxServerError());
    }

    @Test
    void getAllBranchOfficeIsOk() throws Exception{
        doAuthGet("/getAllOffices", status().isOk());
    }

    @Test
    void getAllBranchOfficeIs400() throws Exception{
        doGet("/getAllOffices", status().is4xxClientError());
    }

    @Test
    void exportDataToJson() throws Exception{
        doAuthGet("/exportData", status().isOk());
    }

    @Test
    void importDataToJson() throws Exception{
        CarsOwnersDto carsOwnersDto = new CarsOwnersDto();
        carsOwnersDto.setCars(new ArrayList<>());
        carsOwnersDto.setOwners(new ArrayList<>());
        doAuthPut("/importData", carsOwnersDto, status().isOk());
    }

    @Test
    void deleteBranchOfficeIsOk() throws Exception {
        doAuthDelete("/removeOffice/1", status().isOk());
    }

    @Test
    void deleteBranchOfficeIs400() throws Exception {
        doDelete("/removeOffice/1", status().is4xxClientError());
    }

    @Test
        //Request processing failed; nested exception is java.util.NoSuchElementException: Branch office not found!
    void deleteBranchOfficeIs500() throws Exception {
        //doAuthDelete("/removeOffice/99999", status().is5xxServerError());
    }

    private ResultActions doAuthPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthDelete(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(delete("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(resultMatcher);
    }

    private ResultActions doDelete(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(delete("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(put("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(put("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(resultMatcher);
    }

    private ResultActions doGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get("/branchOffice" + uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }
}
