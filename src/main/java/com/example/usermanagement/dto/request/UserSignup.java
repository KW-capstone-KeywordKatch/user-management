package com.example.usermanagement.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserSignup {
    private String email;
    private String password;
    private String nickname;
    private int age;

}
