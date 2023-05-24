package com.example.usermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class SetEmailTimeRequest {

    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("desired_time")
    private List<String> desiredTime;
}
