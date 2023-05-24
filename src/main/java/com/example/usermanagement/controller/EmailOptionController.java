package com.example.usermanagement.controller;

import com.example.usermanagement.dto.request.SetEmailTimeRequest;
import com.example.usermanagement.dto.response.EmailTimePayload;
import com.example.usermanagement.dto.response.base.BaseResponse;
import com.example.usermanagement.persistence.entity.User;
import com.example.usermanagement.service.EmailOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/emailTimeOption")
public class EmailOptionController {

    private final EmailOptionService emailOptionService;

    @PostMapping("")
    public BaseResponse<EmailTimePayload> updateEmailTime(
            @RequestBody SetEmailTimeRequest request) {
        EmailTimePayload payload = emailOptionService.setEmailTime(request);
        return new BaseResponse<>(payload);
    }

}
