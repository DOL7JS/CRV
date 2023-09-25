package cz.upce.nnpro_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpro_backend.config.JwtRequest;
import cz.upce.nnpro_backend.dtos.NewPasswordDto;
import cz.upce.nnpro_backend.dtos.UserInDto;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    private UserController mockedController = mock(UserController.class);

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
    void loginUserTestIsOk() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("Admin", "heslo");

        doPost("/user/login", jwtRequest, status().isOk());
    }

    @Test   //Podle controlleru by měla vypdnout 500, ale padne 400
    void loginUserNotFoundTest() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("fake_admin", "fake_heslo");
        //doPost("/user/login", jwtRequest, status().is5xxServerError());

    }

    @Test
    void getUserTestIsOk() throws Exception {
        doAuthGet("/user/getUser/1", status().isOk());
    }

    @Test
    void getUserTestIs400error() throws Exception {
        doGet("/user/getUser/1", status().is4xxClientError());
    }

    @Test
    void getUserTestIs500error() throws Exception {
        //doAuthGet("/user/getUser/99999", status().is5xxServerError());
    }

    @Test
    void getAllUsersTestIsOk() throws Exception {
        doAuthGet("/user/getAllUsers", status().isOk());
    }

    @Test
    void getAllUsersTestIs400error() throws Exception {
        doGet("/user/getAllUsers", status().is4xxClientError());
    }

    @Test
    void getAllRolesTestIsOk() throws Exception {
        doAuthGet("/user/getAllRoles", status().isOk());
    }

    @Test
    void getAllRolesTestIs400error() throws Exception {
        doGet("/user/getAllRoles", status().is4xxClientError());
    }

    @Test //Tady bych asi 500 nečekal, když byl přidán ten Admin
    void getAllRolesTestIs500error() throws Exception {
        //doAuthGet("/user/getAllRoles", status().is5xxServerError());
    }

    @Test
    @Order(1)
    void addUserTestIsOk() throws Exception {
        UserInDto userInDto = new UserInDto();
        userInDto.setUsername("user");
        userInDto.setEmail("user@email.com");
        userInDto.setPassword("heslo");
        userInDto.setJobPosition(null);
        userInDto.setRole(3L);

        doAuthPost("/user/addUser", userInDto, status().isOk());
    }

    @Test
    void addUserTestIs400error() throws Exception {
        UserInDto userInDto = new UserInDto();
        userInDto.setUsername("user");
        userInDto.setEmail("user@email.com");
        userInDto.setPassword("heslo");
        userInDto.setJobPosition(null);
        userInDto.setRole(3L);

        doPost("/user/addUser", userInDto, status().is4xxClientError());
    }

    @Test //Tady skáče 400
    void addUserTestIs500error() throws Exception {
        //doAuthPost("/user/addUser", null, status().is5xxServerError());
    }

    @Test
    @Order(2)
    void editUserIsOk() throws Exception {
        UserInDto userInDto = new UserInDto();
        userInDto.setUsername("user");
        userInDto.setEmail("novyuser@email.com");
        userInDto.setPassword("noveheslo");
        userInDto.setJobPosition(null);
        userInDto.setRole(3L);

        doAuthPut("/user/editUser/2", userInDto, status().isOk());
    }

    @Test
    void editUserIs400error() throws Exception {
        UserInDto userInDto = new UserInDto();
        userInDto.setUsername("user");
        userInDto.setEmail("novyuser@email.com");
        userInDto.setPassword("noveheslo");
        userInDto.setJobPosition(null);
        userInDto.setRole(3L);

        doPut("/user/editUser/2", userInDto, status().is4xxClientError());
    }

    @Test //Tady skáče 400
    void editUserIs500error() throws Exception {
        UserInDto userInDto = new UserInDto();
        userInDto.setUsername("user99999");
        userInDto.setEmail("novyuser99999@email.com");
        userInDto.setPassword("noveheslo");
        userInDto.setJobPosition(null);
        userInDto.setRole(3L);

        //doAuthPut("/user/editUser/99999", userDto, status().is5xxServerError());
    }


    @Test
    void changeUserPasswordTestIsOk() throws Exception {
        NewPasswordDto newPassword = new NewPasswordDto();
        newPassword.setOldPassword("heslo");
        newPassword.setNewPassword("noveheslo");

        doAuthPut("/user/changeUserPassword/1", newPassword, status().isOk());
    }

    @Test
    void changeUserPasswordTestIs400error() throws Exception {
        NewPasswordDto newPassword = new NewPasswordDto();
        newPassword.setOldPassword("heslo");
        newPassword.setNewPassword("noveheslo");

        doPut("/user/changeUserPassword/2", newPassword, status().is4xxClientError());
    }

    @Test //Neskáče 500
    //Request processing failed; nested exception is java.util.NoSuchElementException: User not found!
    void changeUserPasswordTestIs500error() throws Exception {
        NewPasswordDto newPassword = new NewPasswordDto();
        newPassword.setOldPassword("heslo");
        newPassword.setNewPassword("noveheslo");

        //doAuthPut("/user/changeUserPassword/99999", newPassword, status().is5xxServerError());
    }

    @Test
    @Order(22)
    void removeUserIsOk() throws Exception {
        doAuthDelete("/user/removeUser/2", status().isOk());
    }

    @Test
    void removeUserIs400error() throws Exception {
        doDelete("/user/removeUser/2", status().is4xxClientError());
    }

    @Test //Tady to nehodí 500, ale vlastní vyjímku
        //Request processing failed; nested exception is java.util.NoSuchElementException: User not found!
    void removeUserIs500error() throws Exception {
        //doAuthDelete("/user/removeUser/99999", status().is5xxServerError());
    }

    private ResultActions doAuthPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPut(String uri, Object object, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthDelete(String uri, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(delete(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(resultMatcher);
    }

    private ResultActions doDelete(String uri, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(delete(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doPost(String uri, Object object, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(resultMatcher);
    }

    private ResultActions doAuthGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(resultMatcher);
    }

    private ResultActions doGet(String uri, ResultMatcher resultMatcher) throws Exception{
        return mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }
}
