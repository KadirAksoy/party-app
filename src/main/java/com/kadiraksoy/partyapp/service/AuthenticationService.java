package com.kadiraksoy.partyapp.service;

import com.kadiraksoy.partyapp.dto.user.JwtAuthenticationResponse;
import com.kadiraksoy.partyapp.dto.user.UserLoginRequest;
import com.kadiraksoy.partyapp.dto.user.UserRegisterRequest;
import com.kadiraksoy.partyapp.mapper.user.UserMapper;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.UserRepository;
import com.kadiraksoy.partyapp.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationService(
            UserRepository userRepository,
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    //Yeni bir kullanıcının kaydını oluşturur.
    public String signup(UserRegisterRequest request) {

        User user = userMapper.registerRequestToEntity(request);

        user = userService.save(user);


//        var jwt = jwtService.generateToken(user);
//
//        JwtAuthenticationResponse.builder().token(jwt).build();
        return "kadir";
    }


    //Kullanıcı girişini işler.
    public JwtAuthenticationResponse signin(UserLoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
