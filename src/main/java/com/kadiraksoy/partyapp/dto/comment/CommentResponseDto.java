package com.kadiraksoy.partyapp.dto.comment;

import com.kadiraksoy.partyapp.dto.party.PartyResponseDto;
import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private UserResponseDto user;
    private PartyResponseDto party;
    private String text;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
