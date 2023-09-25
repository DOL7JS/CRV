package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.Role;
import cz.upce.nnpro_backend.entities.User;
import cz.upce.nnpro_backend.dtos.NewPasswordDto;
import cz.upce.nnpro_backend.dtos.UserOutDto;
import cz.upce.nnpro_backend.dtos.UserInDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @Order(1)
    void addUserTest(){
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(1L);
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username");

        User user = userService.addUser(userInDto);

        assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    void editUserTest(){
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(1L);
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username2");

        User saveUser = userService.addUser(userInDto);

        UserInDto editUserInDto = new UserInDto();
        editUserInDto.setRole(1L);
        editUserInDto.setPassword("heslo");
        editUserInDto.setEmail("email@email.com");
        editUserInDto.setJobPosition("JobPosition");
        editUserInDto.setUsername("username_changed");

        User user = userService.editUser(saveUser.getId(), editUserInDto);

        assertNotNull(user);
        assertEquals("username_changed", user.getUsername());
    }

    @Test
    @Order(3)
    void changePasswordTest(){
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(1L);
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username3");

        User saveUser = userService.addUser(userInDto);

        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setOldPassword("heslo");
        newPasswordDto.setNewPassword("password");
        userService.changePassword(saveUser.getId(), newPasswordDto);
    }

    @Test
    @Order(4)
    void getUserTest(){
        UserOutDto user = userService.getUser(1L);
        assertNotNull(user);
        assertEquals(3,user.getRole().getId());
    }

    @Test
    @Order(5)
    void getAllRolesTest(){
        List<Role> list = userService.getAllRoles();
        assertEquals(3, list.size());
    }

    @Test
    @Order(6)
    void getAllUsersTest(){
        List<UserOutDto> list = userService.getAllUsers();
        assertEquals(4, list.size());
    }

    @Test
    @Order(7)
    void removeUserTest(){
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(1L);
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username5");

        User saveUser = userService.addUser(userInDto);

        User user = userService.removeUser(saveUser.getId());
        assertNotNull(user);
    }

}
