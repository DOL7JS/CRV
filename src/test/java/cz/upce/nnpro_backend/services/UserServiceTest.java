package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.config.WebSecurityConfig;
import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.Role;
import cz.upce.nnpro_backend.entities.User;
import cz.upce.nnpro_backend.dtos.NewPasswordDto;
import cz.upce.nnpro_backend.dtos.UserOutDto;
import cz.upce.nnpro_backend.dtos.UserInDto;
import cz.upce.nnpro_backend.repositories.BranchOfficeRepository;
import cz.upce.nnpro_backend.repositories.RoleRepository;
import cz.upce.nnpro_backend.repositories.UserRepository;
import cz.upce.nnpro_backend.security.SecurityConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BranchOfficeRepository branchOfficeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @AfterEach
    @BeforeEach
    void clearData() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        branchOfficeRepository.deleteAll();
    }

    @Test
    void addUserTest() {
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(1L);
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username");

        User user = userService.addUser(userInDto);

        assertNotNull(user.getId());
        assertEquals(userInDto.getUsername(), user.getUsername());
    }

    @Test
    void editUserTest() {
        Role role = roleRepository.save(new Role("ROLE", "ROLE_desc"));
        BranchOffice office = branchOfficeRepository.save(new BranchOffice("District", "Region", "City"));
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(role.getId());
        userInDto.setOffice(office.getId());
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username2");
        User saveUser = userService.addUser(userInDto);

        UserInDto editUserInDto = new UserInDto();
        editUserInDto.setRole(1L);
        editUserInDto.setPassword("heslo");
        editUserInDto.setEmail("email1@email.com");
        editUserInDto.setJobPosition("JobPosition1");
        editUserInDto.setUsername("username_changed");
        editUserInDto.setOffice(office.getId());
        User user = userService.editUser(saveUser.getId(), editUserInDto);

        assertNotNull(user);
        assertEquals(editUserInDto.getUsername(), user.getUsername());
        assertEquals(editUserInDto.getEmail(), user.getEmail());
        assertEquals(editUserInDto.getJobPosition(), user.getJobPosition());
    }

    @Test
    void changePasswordTest() {
        String newPassword = "password";
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(1L);
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username3");

        User saveUser = userService.addUser(userInDto);

        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setOldPassword("heslo");
        newPasswordDto.setNewPassword(newPassword);
        User user = userService.changePassword(saveUser.getId(), newPasswordDto);
        assertTrue(bCryptPasswordEncoder.matches(newPassword, user.getPassword()));
    }

    @Test
    void getUserTest() {
        UserInDto userInDto = new UserInDto();
        userInDto.setRole(1L);
        userInDto.setPassword("heslo");
        userInDto.setEmail("email@email.com");
        userInDto.setJobPosition("JobPosition");
        userInDto.setUsername("username3");

        User saveUser = userService.addUser(userInDto);


        UserOutDto user = userService.getUser(saveUser.getId());
        assertNotNull(user);
        assertEquals(userInDto.getUsername(), user.getUsername());
        assertEquals(userInDto.getJobPosition(), user.getJobPosition());
        assertEquals(userInDto.getEmail(), user.getEmail());
    }

    @Test
    void getAllUsersTest() {
        UserInDto user1 = new UserInDto();
        user1.setPassword("hesloheslo");
        user1.setEmail("email@email.com");
        user1.setJobPosition("JobPosition");
        user1.setUsername("username2");

        UserInDto user2 = new UserInDto();
        user2.setPassword("hesloheslo");
        user2.setEmail("email1@email.com");
        user2.setJobPosition("JobPosition1");
        user2.setUsername("username_changed");

        User saveUser1 = userService.addUser(user1);
        User saveUser2 = userService.addUser(user2);

        List<UserOutDto> list = userService.getAllUsers();
        assertEquals(2, list.size());//+ 1 is default user Admin
        assertEquals(saveUser1.getUsername(), list.get(0).getUsername());
        assertEquals(saveUser2.getUsername(), list.get(1).getUsername());

    }

    @Test
    void removeUserTest() {
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
