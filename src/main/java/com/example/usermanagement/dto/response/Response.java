package com.example.usermanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    @JsonProperty("is_success")
    private boolean isSuccess;
    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object payload;
}
