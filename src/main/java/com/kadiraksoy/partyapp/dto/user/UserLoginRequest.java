package com.kadiraksoy.partyapp.dto.user;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {

    private String email;
    private String password;
}
