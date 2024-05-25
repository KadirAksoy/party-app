package com.kadiraksoy.partyapp.dto.like;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeRequestDto {

    private Long userId;
    private Long partyId;
}
