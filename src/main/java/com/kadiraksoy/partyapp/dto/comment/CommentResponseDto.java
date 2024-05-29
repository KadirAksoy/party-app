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
    private String text;
    private Long user;
    private Long party;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
