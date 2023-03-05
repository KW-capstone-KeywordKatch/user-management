package com.example.usermanagement.service;

import com.example.usermanagement.dto.request.UserDto;
import com.example.usermanagement.dto.response.Response;
import com.example.usermanagement.dto.response.UserSigninPayload;
import com.example.usermanagement.dto.response.UserSignupPayload;
import com.example.usermanagement.persistence.dao.UserDao;
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

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Response signupUser(UserDto userDto) {

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

        Response response = new Response();

        Optional<User> optUser = userDao.findByEmail(userDto.getEmail());
        if (!optUser.isEmpty()) {
            setSignUpFailResult(response);
            return response;
        }

        User savedUser = userDao.save(user);

        if(!savedUser.getNickname().isEmpty()) {
            setSuccessResult(response, new UserSignupPayload(user.getUserId()));
        } else {
            setSignUpFailResult(response);
        }
        return response;
    }

    public Response signinUser(UserDto userDto) {
        Optional<User> optUser = userDao.findByEmail(userDto.getEmail());
        Response response = new Response();
        if (optUser.isEmpty()) {
            setSignInFailResult(response, 2002);
            return response;
        }
        User user = optUser.get();

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            setSignInFailResult(response, 2003);
            return response;
        }
        //패스워드 일치

        UserSigninPayload payload = UserSigninPayload.builder()
                .userId(user.getUserId())
                .token(jwtTokenProvider.createToken(user.getUserId(), user.getRoles()))
                .build();
        setSuccessResult(response, payload);
        return response;
    }

    private static void setSuccessResult(Response response, Object payload) {
        response.setSuccess(true);
        response.setCode(1000);
        response.setPayload(payload);
    }
    private static void setSignUpFailResult(Response response) {
        response.setSuccess(false);
        response.setCode(2001);
        response.setPayload(null);
    }
    private static void setSignInFailResult(Response response, int code) {
        response.setSuccess(false);
        response.setCode(code);
        response.setPayload(null);
    }



    public Response checkDuplicateNickname(String nickname){
        Response response = new Response();

        Optional<User> optUser = userDao.findByNickname(nickname);
        if (optUser.isEmpty()) {
            response.setCode(1001);
            response.setSuccess(true);
            response.setPayload(new UserSignupPayload(0L));
            return response;
        } else {
            response.setCode(1002);
            response.setSuccess(true);
            response.setPayload(new UserSignupPayload(optUser.get().getUserId()));
            return response;
        }
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
