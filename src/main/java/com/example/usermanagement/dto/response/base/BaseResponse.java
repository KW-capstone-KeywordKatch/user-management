package com.example.usermanagement.dto.response.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;

import static com.example.usermanagement.dto.response.base.BaseResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"is_success", "code", "payload"})
public class BaseResponse<T> {

    @JsonProperty("is_success")
    private boolean isSuccess;
    private int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    // 요청 성공
    public BaseResponse(T payload) {
        this.isSuccess = SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.payload = payload;
    }

    // 요청 실패
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
    }
}
