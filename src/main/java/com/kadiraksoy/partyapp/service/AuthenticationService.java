package com.kadiraksoy.partyapp.service;

import com.kadiraksoy.partyapp.dto.user.JwtAuthenticationResponse;
import com.kadiraksoy.partyapp.dto.user.UserLoginRequest;
import com.kadiraksoy.partyapp.dto.user.UserRegisterRequest;
import com.kadiraksoy.partyapp.exception.UserNotActiveException;
import com.kadiraksoy.partyapp.mapper.user.UserMapper;
import com.kadiraksoy.partyapp.model.user.Role;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.UserRepository;
import com.kadiraksoy.partyapp.security.JwtService;
import com.kadiraksoy.partyapp.util.EmailUtil;
import com.kadiraksoy.partyapp.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            UserMapper userMapper,
            OtpUtil otpUtil,
            EmailUtil emailUtil,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.emailService = emailService;
    }

    //Yeni bir kullanıcının kaydını oluşturur.
    public String signup(UserRegisterRequest request) {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(request.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBirthdayDate(request.getBirthdayDate());
        user.setRole(Role.ROLE_USER);
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());

        userRepository.save(user);

        return "User registration successful";
    }
    public String verifyAccount(String email, String otp) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)) {
            user.setActive(true);
            userRepository.save(user);
            emailService.sendMail(email, user.getFirstName(), user.getLastName(), "Welcomte to Party!!");
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    //Kullanıcı girişini işler.
    public JwtAuthenticationResponse signin(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password.")));

        if(optionalUser.isPresent() && optionalUser.get().isActive()){
            var jwt = jwtService.generateToken(optionalUser.get());

            return JwtAuthenticationResponse.builder().token(jwt).build();
        }throw new UserNotActiveException("User not active exception.");

    }

//    public String updatePassword(Long id,String password){
//
//    }



}
