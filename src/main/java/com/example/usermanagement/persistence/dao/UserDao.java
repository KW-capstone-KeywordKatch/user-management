package com.example.usermanagement.persistence.dao;

import com.example.usermanagement.persistence.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);

    List<User> findAll();

    /**
     * @param user
     * @return success: removed userId / fail: 0
     */
    Long remove(User user);
}
