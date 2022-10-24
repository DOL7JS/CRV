package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.Role;
import cz.upce.nnpro_backend.Entities.User;
import cz.upce.nnpro_backend.config.WebSecurityConfig;
import cz.upce.nnpro_backend.dtos.ChangePasswordDto;
import cz.upce.nnpro_backend.dtos.UserDetailOutDto;
import cz.upce.nnpro_backend.dtos.UserDto;
import cz.upce.nnpro_backend.repositories.RoleRepository;
import cz.upce.nnpro_backend.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       WebSecurityConfig webSecurityConfig
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.webSecurityConfig = webSecurityConfig;
    }

    public User addUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("The username already exists.");
        }
        Optional<Role> role = roleRepository.findById(userDto.getRole() == null ? 0 : userDto.getRole());
        User user = ConversionService.convertToUser(userDto, role);
        user.setPassword(webSecurityConfig.passwordEncoder().encode(user.getPassword()));
        User save = userRepository.save(user);
        return save;
    }

    public User removeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        userRepository.deleteById(userId);
        return user;
    }

    public User editUser(Long userId, UserDto userDto) {
        if (userRepository.existsByUsernameAndIdIsNot(userDto.getUsername(), userId)) {
            throw new IllegalArgumentException("The username already exists.");
        }
        Optional<Role> role = roleRepository.findById(userDto.getRole() == null ? 0 : userDto.getRole());
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        User editedUser = ConversionService.convertToUser(userDto, user, role);
        User save = userRepository.save(editedUser);
        return save;
    }

    public User changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        if (bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
            User save = userRepository.save(user);
            return save;
        }
        throw new IllegalArgumentException("Old password doen't match!");

    }

    public UserDetailOutDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        UserDetailOutDto detailOutDto = ConversionService.convertToUserDetailOutDto(user);
        return detailOutDto;
    }

    public List<Role> getAllRoles() {
        List<Role> all = roleRepository.findAll();
        return all;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.count() != 3) {
            roleRepository.deleteAll();
            roleRepository.save(new Role("ROLE_Kraj", "Krajský pracovník CRV"));
            roleRepository.save(new Role("ROLE_Okres", "Okresní pracovník CRV"));
            roleRepository.save(new Role("ROLE_Admin", "Administrátor"));
        }
        if (!userRepository.existsByRoleName("ROLE_Admin")) {
            User userAdmin = new User();
            String username = "Admin";
            String usernameNew = username;
            int i = 1;
            while (userRepository.existsByUsername(usernameNew)) {
                usernameNew = username + i++;
            }
            userAdmin.setUsername(usernameNew);
            userAdmin.setPassword("$2a$10$MQuBpeE5CbgERbKN7ecd1ea/Y3XwpfWVOqKFErLjbhT382.Rgviy.");
            Role role_admin = roleRepository.findByName("ROLE_Admin");
            userAdmin.setRole(role_admin);
            userRepository.save(userAdmin);

        }
        System.out.println("TESST");
    }
}
