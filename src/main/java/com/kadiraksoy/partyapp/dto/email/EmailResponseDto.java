package com.kadiraksoy.partyapp.dto.email;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponseDto {
    private List<String> emails;
    private String message;
}
