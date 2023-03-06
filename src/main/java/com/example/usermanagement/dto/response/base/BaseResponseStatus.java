package com.example.usermanagement.dto.response.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    SUCCESS(true, 1000),
    FAIL_SIGNUP_DUP_EMAIL(false, 2001),
    FAIL_SIGNIN_NOT_EXIST(false, 2002),
    FAIL_SIGNIN_NOT_MATCH_PW(false, 2003),
    NOT_DUPLICATED_NICKNAME(true, 1001),
    DUPLICATED_NICKNAME(true, 1002);


    private final boolean isSuccess;
    private final int code;

    private BaseResponseStatus(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }
}
