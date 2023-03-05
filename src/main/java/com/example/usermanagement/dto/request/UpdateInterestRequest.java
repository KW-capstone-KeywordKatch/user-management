package com.example.usermanagement.dto.request;

import com.example.usermanagement.persistence.value.Interest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * 관심분야 추가/삭제 요청
 */

@Getter
public class UpdateInterestRequest {

    @JsonProperty("user_id")
    private Long userId;
    // 기본적으로 Jackson은 직렬화/역직렬화할때 enum name을 사용한다.
    private List<Interest> interest;
}
