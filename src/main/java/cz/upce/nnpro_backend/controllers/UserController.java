package cz.upce.nnpro_backend.controllers;


import cz.upce.nnpro_backend.Entities.User;
import cz.upce.nnpro_backend.config.JwtRequest;
import cz.upce.nnpro_backend.config.JwtResponse;
import cz.upce.nnpro_backend.config.JwtTokenUtil;
import cz.upce.nnpro_backend.dtos.ChangePasswordDto;
import cz.upce.nnpro_backend.dtos.UserDto;
import cz.upce.nnpro_backend.repositories.UserRepository;
import cz.upce.nnpro_backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@CrossOrigin
@SecurityRequirement(name = "NNPRO_API")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService jwtInMemoryUserDetailsService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenUtil jwtTokenUtil, UserDetailsService jwtInMemoryUserDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
    }
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
    @GetMapping("/getAllRoles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @DeleteMapping("/removeUser/{userId}")
    public ResponseEntity<?> removeOwner(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.removeUser(userId));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/editUser/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.editUser(userId, userDto));
    }

    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/changeUserPassword/{userId}")
    public ResponseEntity<?> changeUserPassword(@PathVariable Long userId, @RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(userService.changePassword(userId, changePasswordDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final User user = userRepository.findByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), user.getId(), userDetails.getAuthorities()));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
