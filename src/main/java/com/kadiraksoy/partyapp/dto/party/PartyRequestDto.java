package com.kadiraksoy.partyapp.dto.party;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartyRequestDto {

    private String name;
    private String description;
    private LocalDateTime eventDate;
    private LocalDateTime registrationEndDate;
    private int maxParticipants;
}
