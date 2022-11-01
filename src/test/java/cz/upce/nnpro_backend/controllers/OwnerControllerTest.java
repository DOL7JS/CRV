package cz.upce.nnpro_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpro_backend.config.JwtRequest;
import cz.upce.nnpro_backend.dtos.OwnerDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OwnerControllerTest {

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
    void getAllOwnerIsOk() throws Exception {
        doAuthGet("/getAllOwner", status().isOk());
    }

    @Test
    void getAllOwnerIs400() throws Exception {
        doGet("/getAllOwner", status().is4xxClientError());
    }

    @Test //Asi bych čekal prazdnej list než chybu
    void getAllOwnerIs500() throws Exception {
        //doAuthGet("/getAllOwner", status().is5xxServerError());
    }

    @Test
    @Order(1)
    void addOwnerIsOk() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setBirthDate(LocalDate.now());
        ownerDto.setCity("City");
        ownerDto.setFirstName("FirstName");
        ownerDto.setLastName("LastName");
        ownerDto.setStreet("Street");
        ownerDto.setZipCode(123456);
        ownerDto.setNumberOfHouse(1);

        doAuthPost("/addOwner", ownerDto, status().isOk());
    }

    @Test
    void addOwnerIs400() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setBirthDate(LocalDate.now());
        ownerDto.setCity("City");
        ownerDto.setFirstName("FirstName");
        ownerDto.setLastName("LastName");
        ownerDto.setStreet("Street");
        ownerDto.setZipCode(123456);
        ownerDto.setNumberOfHouse(1);

        doPost("/addOwner", ownerDto, status().is4xxClientError());
    }

    @Test
    void addOwnerIs500() throws Exception {
        //doAuthPost("/addOwner", null, status().is5xxServerError());
    }

    @Test
    @Order(2)
    void getOwnerIsOk() throws Exception {
        doAuthGet("/getOwner/1", status().isOk());
    }

    @Test
    void getOwnerIs400() throws Exception {
        doGet("/getOwner/1", status().is4xxClientError());
    }

    @Test
    void getOwnerIs500() throws Exception {
        //doAuthGet("/getOwner/99999", status().is5xxServerError());
    }

    @Test
    @Order(3)
    void editOwnerIsOk() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setBirthDate(LocalDate.now());
        ownerDto.setCity("City");
        ownerDto.setFirstName("FirstName");
        ownerDto.setLastName("LastName");
        ownerDto.setStreet("Street");
        ownerDto.setZipCode(123456);
        ownerDto.setNumberOfHouse(1);

        doAuthPut("/editOwner/1", ownerDto, status().isOk());
    }

    @Test
    void editOwnerIs400() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setBirthDate(LocalDate.now());
        ownerDto.setCity("City");
        ownerDto.setFirstName("FirstName");
        ownerDto.setLastName("LastName");
        ownerDto.setStreet("Street");
        ownerDto.setZipCode(123456);
        ownerDto.setNumberOfHouse(1);

        doPut("/editOwner/1", ownerDto, status().is4xxClientError());
    }

    @Test
    void editOwnerIs500() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setBirthDate(LocalDate.now());
        ownerDto.setCity("City");
        ownerDto.setFirstName("FirstName");
        ownerDto.setLastName("LastName");
        ownerDto.setStreet("Street");
        ownerDto.setZipCode(123456);
        ownerDto.setNumberOfHouse(1);

        //doAuthPut("/editOwner/99999", ownerDto, status().is5xxServerError());
    }

    @Test
    @Order(15)
    void removeOwnerIsOk() throws Exception {
        doAuthDelete("/removeOwner/1", status().isOk());
    }

    @Test
    void removeOwnerIs400() throws Exception {
        doDelete("/removeOwner/1", status().is4xxClientError());
    }

    @Test
    void removeOwnerIs500() throws Exception {
        //doAuthDelete("/removeOwner/99999", status().is5xxServerError());
    }

    private ResultActions doAuthPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthDelete(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(delete("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(resultMatcher);
    }

    private ResultActions doDelete(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(delete("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(put("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(put("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(resultMatcher);
    }

    private ResultActions doGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get("/owner" + uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }
}
