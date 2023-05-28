package com.example.usermanagement.controller;

import com.example.usermanagement.dto.response.base.BaseResponse;
import com.example.usermanagement.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 키워드 관련 API
 **/
@RestController
@RequestMapping("/keywords/trend")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("")
    public BaseResponse<List<String>> keywordTrend() {
        List<String> payload = keywordService.getPopularKeywords();

        return new BaseResponse<>(payload);
    }

}
