package com.example.usermanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSigninPayload {

    @JsonProperty("user_id")
    private Long userId;

    private String token;
}
