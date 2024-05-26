package com.kadiraksoy.partyapp.dto.file;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Before;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageRequestDto {

    private String name;
    private String type;
    private byte[] imageData;

}
