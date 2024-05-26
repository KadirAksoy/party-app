package com.kadiraksoy.partyapp.mapper.user;


import com.kadiraksoy.partyapp.dto.user.UserLoginRequest;
import com.kadiraksoy.partyapp.dto.user.UserRegisterRequest;
import com.kadiraksoy.partyapp.dto.user.UserRequestDto;
import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
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

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getUsername());
        dto.setLastName(user.getLastName());
        dto.setCreatedTime(user.getCreatedTime());
        dto.setUpdatedTime(user.getUpdatedTime());
        return dto;
    }



}
