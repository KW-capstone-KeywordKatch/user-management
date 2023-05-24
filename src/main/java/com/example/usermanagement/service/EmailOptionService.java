package com.example.usermanagement.service;

import com.example.usermanagement.dto.request.SetEmailTimeRequest;
import com.example.usermanagement.dto.response.EmailTimePayload;
import com.example.usermanagement.persistence.dao.UserRepository;
import com.example.usermanagement.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailOptionService {

    private final UserRepository userRepository;

    /*
    * 클라이언트로부터 전달받은 시각 목록은 기존의 시각에 원하는 시각이 추가되었거나 빠진 형태이다.
    *  */
    public EmailTimePayload setEmailTime(SetEmailTimeRequest setEmailTimeRequest) {

        User user = userRepository.getReferenceById(setEmailTimeRequest.getUserId());
        user.setEmailTime(setEmailTimeRequest.getDesiredTime());
        return new EmailTimePayload(user.getEmailTime());
    }
}
