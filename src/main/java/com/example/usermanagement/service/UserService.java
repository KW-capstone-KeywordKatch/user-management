package com.example.usermanagement.service;

import com.example.usermanagement.dto.request.UserDto;
import com.example.usermanagement.persistence.dao.UserDao;
import com.example.usermanagement.persistence.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserDao userDao;

    public Long signupUser(User user) {
        User savedUser = userDao.save(user);
        return savedUser.getUserId();
    }

    /**
     * @param nickname
     * @return userId (없으면 0)
     */
    public Long checkDuplicateNickname(String nickname){
        for (User user : userDao.findAll()) {
            if (user.getNickname().equals(nickname)) {
                return user.getUserId();
            }
        }
        return 0L;
    }

    public Long removeUser(UserDto userDto) {
        for (User user : userDao.findAll()) {
            if (user.getEmail().equals(userDto.getEmail())) {
                return userDao.remove(user);
            }
        }

        return 0L;
    }
}
