package com.kadiraksoy.partyapp.service;
import com.kadiraksoy.partyapp.exception.UserAlreadyExistException;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public User save(User newUser) {
        Optional<User> user = userRepository.findByEmail(newUser.getEmail());
        if (user.isEmpty()) {
            newUser.setCreatedTime(LocalDateTime.now());
            return userRepository.save(newUser);
        } throw new UserAlreadyExistException("User already exits with email:" + user.get().getEmail());
    }

}