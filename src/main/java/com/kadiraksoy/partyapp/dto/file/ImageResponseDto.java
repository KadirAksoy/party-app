package com.kadiraksoy.partyapp.dto.file;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponseDto {

    private Long id;
    private String name;
    private String type;
    private byte[] imageData;
    private Long party;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
