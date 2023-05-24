package com.example.usermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class UserDto {
    private String email;
    private String password;
    private String nickname;
    @JsonProperty("desired_time")
    private List<String> emailTime = null;
}
