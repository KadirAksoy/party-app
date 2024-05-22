package com.kadiraksoy.partyapp.mapper.user;


import com.kadiraksoy.partyapp.dto.user.UserLoginRequest;
import com.kadiraksoy.partyapp.dto.user.UserRegisterRequest;
import com.kadiraksoy.partyapp.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User registerRequestToEntity(UserRegisterRequest userRegisterRequest){
        return User.builder()
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .email(userRegisterRequest.getEmail())
                .password(userRegisterRequest.getPassword())
                .birthdayDate(userRegisterRequest.getBirthdayDate())
                .build();

    }

}
