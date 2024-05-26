package com.kadiraksoy.partyapp.dto.party;

import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
import com.kadiraksoy.partyapp.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartyResponseDto {

    private Long id;
    private String title;
    private String description;
    private Date eventDate;
    private int participantLimit;
    private Date createdTime;
    private Date updatedTime;
    private UserResponseDto admin;
    private List<UserResponseDto> participants;
    private Long imageId;
    private boolean active;
}
