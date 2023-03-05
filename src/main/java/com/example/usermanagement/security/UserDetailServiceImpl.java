package com.example.usermanagement.security;

import com.example.usermanagement.persistence.dao.UserRepository;
import com.example.usermanagement.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    private final UserRepository userRepository;

    /*
     * 회원 정보를 불러와서 UserDetails로 반환한다.
     * username은 회원을 식별할수 있는 값이다. (PK 사용했음)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User desiredUser;
        Long userId = Long.valueOf(username);
        LOGGER.info("[loadUserByUsername] username: {} ", username);

        desiredUser = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("no such user"));

        return new CustomUserDetails(desiredUser);
    }
}
