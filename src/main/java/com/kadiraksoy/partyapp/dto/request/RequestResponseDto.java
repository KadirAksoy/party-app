package com.kadiraksoy.partyapp.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponseDto {

    private Long id;
    private String email;
    private boolean accept;
    private Date createdTime;
    private Date updatedTime;
}
