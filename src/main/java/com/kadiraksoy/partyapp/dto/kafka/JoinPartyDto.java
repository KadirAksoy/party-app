package com.kadiraksoy.partyapp.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinPartyDto{

    private String title;
    private String description;
    private Date eventDate;
    private String firstName;
    private String lastName;
    private String email;


}
