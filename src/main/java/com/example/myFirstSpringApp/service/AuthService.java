package com.example.myFirstSpringApp.service;

import com.example.myFirstSpringApp.config.JwtTokenProvider;
import com.example.myFirstSpringApp.exception.BadRequestException;
import com.example.myFirstSpringApp.model.Role;
import com.example.myFirstSpringApp.model.User;
import com.example.myFirstSpringApp.model.UserRole;
import com.example.myFirstSpringApp.payload.ApiResponse;
import com.example.myFirstSpringApp.payload.JwtAuthenticationResponse;
import com.example.myFirstSpringApp.payload.LoginRequest;
import com.example.myFirstSpringApp.payload.SignUpRequest;
import com.example.myFirstSpringApp.repository.RoleRepository;
import com.example.myFirstSpringApp.repository.UserRepository;
import static com.example.myFirstSpringApp.util.FieldsValidator.validateName;

import static com.example.myFirstSpringApp.util.FieldsValidator.validatePassword;

import com.example.myFirstSpringApp.repository.UserRoleRepository;
import com.example.myFirstSpringApp.util.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.example.myFirstSpringApp.util.FieldsValidator.validateEmail;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public JwtAuthenticationResponse logInUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {


//        if(!validateEmail(signUpRequest.getEmail())) throw new BadRequestException("Email format not valid, check your email address.");
//        if(!validateName(signUpRequest.getUsername())) throw new BadRequestException("Name format not valid, please check your name.");
//        if(!validatePassword(signUpRequest.getPassword())) throw new BadRequestException("Password does not meet the pre requisites, try a different one");

        if(userRepository.existsByUserName(signUpRequest.getUsername())) throw new BadRequestException("Username is already taken!!");

        if(userRepository.existsByEmail(signUpRequest.getEmail())) throw new BadRequestException("Email is already taken!!");

        // Create user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Optional<Role> role = roleRepository.findByName(RoleName.ROLE_USER);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role.get());

        user = userRepository.save(user);
        userRoleRepository.save(userRole);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUserName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User Registered Successfully!"));
    }
}
