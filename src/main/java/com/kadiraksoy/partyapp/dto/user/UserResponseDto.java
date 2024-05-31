package com.kadiraksoy.partyapp.dto.user;


import com.kadiraksoy.partyapp.dto.party.PartyResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Date birthdayDate;
    private String active;
    private String otp;
    private LocalDateTime otpGeneratedTime;
//    private List<PartyResponseDto> partyResponseDtoList;

}
