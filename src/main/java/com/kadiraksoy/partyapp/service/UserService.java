package com.kadiraksoy.partyapp.service;
import com.kadiraksoy.partyapp.exception.UserAlreadyExistException;
import com.kadiraksoy.partyapp.exception.UserNotFoundException;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

//    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public User save(User newUser) {
        Optional<User> user = userRepository.findByEmail(newUser.getEmail());
        if (user.isEmpty()) {
            newUser.setCreatedTime(LocalDateTime.now());
            return userRepository.save(newUser);
        } throw new UserAlreadyExistException("User already exits with email:" + user.get().getEmail());
    }

//    @CachePut(cacheNames = "user_id", key = "'getUserById' + #dto.id", unless = "#result == null")
    public User update(Long id, User user){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            optionalUser.get().setFirstName(user.getFirstName());
            optionalUser.get().setLastName(user.getLastName());
            optionalUser.get().setBirthdayDate(user.getBirthdayDate());
            optionalUser.get().setPassword(user.getPassword());
            userRepository.save(optionalUser.get());
            return optionalUser.get();
        }throw new UserNotFoundException("User not found with id: " + id);

    }
//    @Cacheable(cacheNames = "user_id", key = "#root.methodName + #id", unless = "#result == null")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

//    @Cacheable(value = "users", key = "#root.methodName", unless = "#result == null")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public List<String> getAllEmails(){
        return userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}