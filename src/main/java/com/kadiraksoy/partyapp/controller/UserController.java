package com.kadiraksoy.partyapp.controller;

import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
import com.kadiraksoy.partyapp.exception.UserAlreadyExistException;
import com.kadiraksoy.partyapp.exception.UserNotFoundException;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/super-admin")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok("User saved successfully.");
        } catch (UserAlreadyExistException e) {
            log.error("Error saving user: ", e);
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok("User updated successfully.");
        } catch (UserNotFoundException e) {
            log.error("Error updating user: ", e);
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.error("Error retrieving user: ", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.error("Error retrieving user: ", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = userService.getAllEmails();
        return ResponseEntity.ok(emails);
    }
}
