package com.example.usermanagement.controller;

import com.example.usermanagement.dto.request.UpdateInterestRequest;
import com.example.usermanagement.dto.response.base.BaseResponse;
import com.example.usermanagement.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/interest")
public class InterestController {

    private final InterestService interestService;

    @PatchMapping("")
    public BaseResponse<List<String>> addInterest(@RequestBody UpdateInterestRequest request) {
        List<String> payload;
        payload = interestService.updateInterest(request.getUserId(), request.getInterest());
        return new BaseResponse<>(payload);
    }

    @GetMapping("/{userId}")
    public BaseResponse<List<String>> getInterest(@PathVariable("userId") Long userId) {
        List<String> payload;
        payload = interestService.getUsersInterest(userId);
        return new BaseResponse<>(payload);
    }
}
