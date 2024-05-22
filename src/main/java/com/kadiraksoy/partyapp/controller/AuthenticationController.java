package com.kadiraksoy.partyapp.controller;

import com.kadiraksoy.partyapp.dto.user.JwtAuthenticationResponse;
import com.kadiraksoy.partyapp.dto.user.UserLoginRequest;
import com.kadiraksoy.partyapp.dto.user.UserRegisterRequest;
import com.kadiraksoy.partyapp.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public JwtAuthenticationResponse signup(@RequestBody UserRegisterRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody UserLoginRequest request) {
        return authenticationService.signin(request);
    }
}