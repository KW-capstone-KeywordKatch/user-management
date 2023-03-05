package com.example.usermanagement.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserDto {
    private String email;
    private String password;
    private String nickname;
}
