package com.example.usermanagement.controller;

import com.example.usermanagement.dto.request.UserDto;
import com.example.usermanagement.dto.response.Response;
import com.example.usermanagement.dto.response.UserSignupPayload;
import com.example.usermanagement.persistence.entity.User;
import com.example.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * @param userDto
     * @return Response
     * 중복 검사 되어있다고 가정 null만 검사하는 상태
     */
    @PostMapping("/user/signup")
    public Response signUp(@RequestBody UserDto userDto) {

        Response response = userService.signupUser(userDto);
        return response;
    }

    @PostMapping("/auth/signin")
    public Response signIn(@RequestBody UserDto userDto) throws RuntimeException {
        Response response = userService.signinUser(userDto);

        return response;
    }

    @GetMapping("/user/duplication/{nickname}")
    public Response nicknameCheck(@PathVariable("nickname") String nickname){
        Response response = userService.checkDuplicateNickname(nickname);

        return response;
    }

    /**
     * email, password 를 입력했을 때 삭제
     * 입력한 email이 없으면 fail
     * 해당 email의 password가 일치하는 지는 고려안한 상태
     */
    @DeleteMapping("/user/drop")
    public String dropUser(@RequestBody UserDto userDto) {
        Long dropedId = userService.removeUser(userDto);
        if (dropedId != 0L) {
            System.out.println("drop success");
            return "dropOK";
        }
        else {
            System.out.println("drop fail");
            return "dropFail";
        }
    }
}
