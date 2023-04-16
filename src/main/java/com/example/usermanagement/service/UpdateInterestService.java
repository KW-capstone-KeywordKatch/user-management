package com.example.usermanagement.service;

import com.example.usermanagement.persistence.dao.UserRepository;
import com.example.usermanagement.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateInterestService {

    private final UserRepository userRepository;

    /**
     * 추가/삭제는 프론트엔드에서 실행, 서버는 단순히 그 값을 전달받아 DB에 저장하기만 한다.
     * @param userId
     * @param interests 업데이트된 새로운 관심분야 목록
     * @return 업데이트된 관심분야 목록
     */
    @Transactional
    public List<String> updateInterest(Long userId, List<String> interests) {
        User user;
        user = userRepository.getReferenceById(userId);
        user.setInterests(interests);
        return user.getInterests();
    }

}
