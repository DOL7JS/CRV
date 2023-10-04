package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.Role;
import cz.upce.nnpro_backend.entities.User;
import cz.upce.nnpro_backend.config.WebSecurityConfig;
import cz.upce.nnpro_backend.dtos.NewPasswordDto;
import cz.upce.nnpro_backend.dtos.UserOutDto;
import cz.upce.nnpro_backend.dtos.UserInDto;
import cz.upce.nnpro_backend.repositories.BranchOfficeRepository;
import cz.upce.nnpro_backend.repositories.RoleRepository;
import cz.upce.nnpro_backend.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final BranchOfficeRepository branchOfficeRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       WebSecurityConfig webSecurityConfig,
                       BranchOfficeRepository branchOfficeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.webSecurityConfig = webSecurityConfig;
        this.branchOfficeRepository = branchOfficeRepository;
    }

    public User addUser(UserInDto userInDto) {
        if (userRepository.existsByUsername(userInDto.getUsername())) {
            throw new IllegalArgumentException("The username already exists.");
        }
        Optional<Role> role = roleRepository.findById(userInDto.getRole() == null ? 0 : userInDto.getRole());
        Optional<BranchOffice> office = branchOfficeRepository.findById(userInDto.getOffice() == null ? 0 : userInDto.getOffice());
        User user = ConversionService.convertToUser(userInDto, role, office);
        user.setPassword(webSecurityConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User removeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        userRepository.deleteById(userId);
        return user;
    }

    public User editUser(Long userId, UserInDto userInDto) {
        if (userRepository.existsByUsernameAndIdIsNot(userInDto.getUsername(), userId)) {
            throw new IllegalArgumentException("The username already exists.");
        }
        Optional<Role> role = roleRepository.findById(userInDto.getRole() == null ? 0 : userInDto.getRole());
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        User editedUser = ConversionService.convertToUser(userInDto, user, role);
        return userRepository.save(editedUser);
    }

    public User changePassword(Long userId, NewPasswordDto newPasswordDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        if (bCryptPasswordEncoder.matches(newPasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPasswordDto.getNewPassword()));
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Old password doesn't match!");

    }

    public UserOutDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        return ConversionService.convertToUserDetailOutDto(user);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<UserOutDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserOutDto> userOutDtos = new ArrayList<>();
        for (User user : users) {
            userOutDtos.add(ConversionService.convertToUserDetailOutDto(user));
        }
        return userOutDtos;
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
    }
}
