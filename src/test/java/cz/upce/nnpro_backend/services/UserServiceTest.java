package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.Role;
import cz.upce.nnpro_backend.Entities.User;
import cz.upce.nnpro_backend.dtos.ChangePasswordDto;
import cz.upce.nnpro_backend.dtos.UserDetailOutDto;
import cz.upce.nnpro_backend.dtos.UserDto;
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
        UserDto userDto = new UserDto();
        userDto.setRole(1L);
        userDto.setPassword("heslo");
        userDto.setEmail("email@email.com");
        userDto.setJobPosition("JobPosition");
        userDto.setUsername("username");

        User user = userService.addUser(userDto);

        assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    void editUserTest(){
        UserDto userDto = new UserDto();
        userDto.setRole(1L);
        userDto.setPassword("heslo");
        userDto.setEmail("email@email.com");
        userDto.setJobPosition("JobPosition");
        userDto.setUsername("username2");

        User saveUser = userService.addUser(userDto);

        UserDto editUserDto = new UserDto();
        editUserDto.setRole(1L);
        editUserDto.setPassword("heslo");
        editUserDto.setEmail("email@email.com");
        editUserDto.setJobPosition("JobPosition");
        editUserDto.setUsername("username_changed");

        User user = userService.editUser(saveUser.getId(), editUserDto);

        assertNotNull(user);
        assertEquals("username_changed", user.getUsername());
    }

    @Test
    @Order(3)
    void changePasswordTest(){
        UserDto userDto = new UserDto();
        userDto.setRole(1L);
        userDto.setPassword("heslo");
        userDto.setEmail("email@email.com");
        userDto.setJobPosition("JobPosition");
        userDto.setUsername("username3");

        User saveUser = userService.addUser(userDto);

        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("heslo");
        changePasswordDto.setNewPassword("password");
        userService.changePassword(saveUser.getId(), changePasswordDto);
    }

    @Test
    @Order(4)
    void getUserTest(){
        UserDetailOutDto user = userService.getUser(1L);
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
        List<UserDetailOutDto> list = userService.getAllUsers();
        assertEquals(4, list.size());
    }

    @Test
    @Order(7)
    void removeUserTest(){
        UserDto userDto = new UserDto();
        userDto.setRole(1L);
        userDto.setPassword("heslo");
        userDto.setEmail("email@email.com");
        userDto.setJobPosition("JobPosition");
        userDto.setUsername("username5");

        User saveUser = userService.addUser(userDto);

        User user = userService.removeUser(saveUser.getId());
        assertNotNull(user);
    }

}
