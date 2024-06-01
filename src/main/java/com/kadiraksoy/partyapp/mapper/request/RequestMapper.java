package com.kadiraksoy.partyapp.mapper.request;


import com.kadiraksoy.partyapp.dto.request.RequestDto;
import com.kadiraksoy.partyapp.dto.request.RequestResponseDto;
import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
import com.kadiraksoy.partyapp.model.request.Request;
import com.kadiraksoy.partyapp.model.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestMapper {

    public Request dtoToEntity(RequestDto requestDto){
        return Request.builder()
                .email(requestDto.getEmail())
                .accept(false)
                .build();
    }

    public RequestResponseDto entityToResponseDto(Request request){
        return RequestResponseDto.builder()
                .id(request.getId())
                .createdTime(request.getCreatedTime())
                .updatedTime(request.getUpdatedTime())
                .email(request.getEmail())
                .build();
    }

    public List<RequestResponseDto> toUserResponseDtoList(List<Request> request){
        return request.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toList());
    }
}
