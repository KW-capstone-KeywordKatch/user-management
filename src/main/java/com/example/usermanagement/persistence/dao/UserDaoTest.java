package com.example.usermanagement.persistence.dao;

import com.example.usermanagement.persistence.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

//일단 메모리로 테스트하기 위해서 구현한 클래스
//**DB로 구현한 클래스 추가 필요**
@Repository
public class UserDaoTest implements UserDao{

    private static Map<Long, User> store = new HashMap<>();
    private static Long sequenceId = 0L;

    @Override
    public User save(User user) {
        user.setUserId(++sequenceId);
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return store.values().stream().filter(user -> user.getNickname().equals(nickname)).findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Long remove(User user) {
        if (store.containsKey(user.getUserId())) {
            store.remove(user.getUserId());
            return user.getUserId();
        }
        return 0L;
    }


}
