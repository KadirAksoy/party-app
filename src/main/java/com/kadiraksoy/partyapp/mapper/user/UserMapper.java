package com.kadiraksoy.partyapp.mapper.user;


import com.kadiraksoy.partyapp.dto.user.UserRegisterRequest;
import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
import com.kadiraksoy.partyapp.mapper.party.PartyMapper;
import com.kadiraksoy.partyapp.model.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


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

    public UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreatedTime(user.getCreatedTime());
        dto.setUpdatedTime(user.getUpdatedTime());
        dto.setActive(String.valueOf(user.isActive()));
        dto.setBirthdayDate(user.getBirthdayDate());
        dto.setOtp(user.getOtp());
        dto.setOtpGeneratedTime(user.getOtpGeneratedTime());
        dto.setRole(user.getRole().name());
        return dto;
    }

    public List<UserResponseDto> toUserResponseDtoList(List<User> userList){
        return userList.stream()
                .map(this::toUserResponseDto)
                .collect(Collectors.toList());
    }


}
