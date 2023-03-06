package com.example.usermanagement.service;

import com.example.usermanagement.dto.request.UserDto;
import com.example.usermanagement.dto.response.UserSigninPayload;
import com.example.usermanagement.dto.response.UserSignupPayload;
import com.example.usermanagement.persistence.dao.UserRepository;
import com.example.usermanagement.persistence.entity.User;
import com.example.usermanagement.persistence.value.Role;
import com.example.usermanagement.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserSignupPayload signupUser(UserDto userDto) {

        User user;
        String role = "normal"; //일단 그냥 노말 가입만
        if (role.equalsIgnoreCase("admin")) {
            user = User.builder()
                    .email(userDto.getEmail())
                    .nickname(userDto.getNickname())
                    .roles(Collections.singletonList(Role.ADMIN.toString()))
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();
        } else {
            user = User.builder()
                    .email(userDto.getEmail())
                    .nickname(userDto.getNickname())
                    .roles(Collections.singletonList(Role.NORMAL.toString()))
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();
        }

        Optional<User> optUser = userRepository.findByEmail(userDto.getEmail());
        if (!optUser.isEmpty()) {
            return new UserSignupPayload(0L);
        }

        User savedUser = userRepository.save(user);
        return new UserSignupPayload(savedUser.getUserId());
    }

    public UserSigninPayload signinUser(UserDto userDto) {
        Optional<User> optUser = userRepository.findByEmail(userDto.getEmail());

        if (optUser.isEmpty()) {
            return new UserSigninPayload(0L, "not exist email", null);
        }
        User user = optUser.get();

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return new UserSigninPayload(-1L, "not matched password", null);
        }

        //패스워드 일치
        UserSigninPayload payload = UserSigninPayload.builder()
                .userId(user.getUserId())
                .token(jwtTokenProvider.createToken(user.getUserId(), user.getRoles()))
                .interests(user.getInterests())
                .build();
        return payload;
    }

    public UserSignupPayload checkDuplicateNickname(String nickname){
        Optional<User> optUser = userRepository.findByNickname(nickname);
        if (optUser.isEmpty()) {
            return new UserSignupPayload(0L);
        } else {
            return new UserSignupPayload(optUser.get().getUserId());
        }
    }

    public UserSignupPayload removeUser(UserDto userDto) {

        Optional<User> optUser = userRepository.findByEmail(userDto.getEmail());
        if (optUser.isEmpty()) {
            return new UserSignupPayload(0L);
        }

        userRepository.delete(optUser.get());

        return new UserSignupPayload(optUser.get().getUserId());
    }
}
