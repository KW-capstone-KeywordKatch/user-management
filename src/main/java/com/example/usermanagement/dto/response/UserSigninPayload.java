package com.example.usermanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSigninPayload {

    @JsonProperty("user_id")
    private Long userId;

    private String token;

    private List<String> interests = new ArrayList<>();
}
