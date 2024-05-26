package com.kadiraksoy.partyapp.dto.party;

import com.kadiraksoy.partyapp.dto.user.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartyRequestDto {


    private String title;
    private String description;
    private Date eventDate;
    private int participantLimit;
    private Long adminId;
    private Long imageId;
}
