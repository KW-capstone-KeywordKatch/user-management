package com.example.usermanagement.dto.response.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    SUCCESS(true, 1000);

    private final boolean isSuccess;
    private final int code;

    private BaseResponseStatus(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }
}
