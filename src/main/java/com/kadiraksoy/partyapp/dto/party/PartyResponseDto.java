package com.kadiraksoy.partyapp.dto.party;

import com.kadiraksoy.partyapp.dto.user.UserResponseDto;

import java.time.LocalDateTime;
import java.util.Set;

public class PartyResponseDto {

    private Long id;
    private String name;
    private LocalDateTime eventDate;
    private LocalDateTime registrationEndDate;
    private int maxParticipants;
    private UserResponseDto admin;
    private Set<UserResponseDto> participants;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
