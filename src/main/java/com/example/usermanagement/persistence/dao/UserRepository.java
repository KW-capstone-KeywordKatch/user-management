package com.example.usermanagement.persistence.dao;

import com.example.usermanagement.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u")
    Optional<List<User>> selectEveryUsers();
}
