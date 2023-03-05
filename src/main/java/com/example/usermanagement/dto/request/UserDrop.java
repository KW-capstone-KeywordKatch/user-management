package com.example.usermanagement.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDrop {
    private String email;
    private String password;
}
