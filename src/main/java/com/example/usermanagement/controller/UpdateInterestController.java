package com.example.usermanagement.controller;

import com.example.usermanagement.dto.request.UpdateInterestRequest;
import com.example.usermanagement.dto.response.base.BaseResponse;
import com.example.usermanagement.persistence.value.Interest;
import com.example.usermanagement.service.UpdateInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/interest")
public class UpdateInterestController {

    private final UpdateInterestService updateInterestService;

    @PatchMapping("")
    public BaseResponse<List<Interest>> addInterest(@RequestBody UpdateInterestRequest request) {
        List<Interest> payload;
        payload = updateInterestService.updateInterest(request.getUserId(), request.getInterest());
        return new BaseResponse<>(payload);
    }
}
