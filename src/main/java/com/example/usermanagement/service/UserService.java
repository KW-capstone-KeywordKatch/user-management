package com.example.usermanagement.service;

import com.example.usermanagement.dto.request.UserDrop;
import com.example.usermanagement.persistence.dao.UserDao;
import com.example.usermanagement.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public Long joinUser(User user) {
        User savedUser = userDao.save(user);
        return savedUser.getId();
    }

    /**
     * @param nickname
     * @return userId (없으면 0)
     */
    public Long checkDuplicateNickname(String nickname){
        for (User user : userDao.findAll()) {
            if (user.getNickname().equals(nickname)) {
                return user.getId();
            }
        }
        return 0L;
    }

    public Long removeUser(UserDrop userDrop) {
        for (User user : userDao.findAll()) {
            if (user.getEmail().equals(userDrop.getEmail())) {
                return userDao.remove(user);
            }
        }

        return 0L;
    }
}
