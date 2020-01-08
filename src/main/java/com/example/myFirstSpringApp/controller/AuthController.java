package com.example.myFirstSpringApp.controller;

import com.example.myFirstSpringApp.payload.JwtAuthenticationResponse;
import com.example.myFirstSpringApp.payload.LoginRequest;
import com.example.myFirstSpringApp.payload.SignUpRequest;
import com.example.myFirstSpringApp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping( value = "/signin", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest  loginRequest){
        JwtAuthenticationResponse jwtAuthenticationResponse= authService.logInUser(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthenticationResponse);
    }

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signup (@Valid @RequestBody SignUpRequest signUpRequest){
        ResponseEntity<?> result =  authService.registerUser(signUpRequest);
        return result;
    }

}
