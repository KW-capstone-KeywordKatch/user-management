package com.example.usermanagement.controller;

import com.example.usermanagement.dto.request.UserDto;
import com.example.usermanagement.dto.response.UserSigninPayload;
import com.example.usermanagement.dto.response.UserSignupPayload;
import com.example.usermanagement.dto.response.base.BaseResponse;
import com.example.usermanagement.dto.response.base.BaseResponseStatus;
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
    public BaseResponse signUp(@RequestBody UserDto userDto) {

        UserSignupPayload payload = userService.signupUser(userDto);

        if (payload.getUserId() == 0L) {
            return new BaseResponse<>(BaseResponseStatus.FAIL_SIGNUP_DUP_EMAIL);
        }

        return new BaseResponse<>(payload);
    }

    @PostMapping("/auth/signin")
    public BaseResponse signIn(@RequestBody UserDto userDto) {
        UserSigninPayload payload = userService.signinUser(userDto);

        if (payload.getUserId() == 0L) {
            return new BaseResponse(BaseResponseStatus.FAIL_SIGNIN_NOT_EXIST);
        } else if (payload.getUserId() == -1L) {
            return new BaseResponse(BaseResponseStatus.FAIL_SIGNIN_NOT_MATCH_PW);
        }

        return new BaseResponse(payload);
    }

    @GetMapping("/user/duplication/{nickname}")
    public BaseResponse nicknameCheck(@PathVariable("nickname") String nickname){
        UserSignupPayload payload = userService.checkDuplicateNickname(nickname);

        if (payload.getUserId() == 0L) {
            return new BaseResponse(BaseResponseStatus.NOT_DUPLICATED_NICKNAME);
        } else {
            return new BaseResponse(payload);
        }
    }

    /**
     * email, password 를 입력했을 때 삭제
     * 입력한 email이 없으면 fail
     * 해당 email의 password가 일치하는 지는 고려안한 상태
     */
    @DeleteMapping("/user/drop")
    public BaseResponse dropUser(@RequestBody UserDto userDto) {
        UserSignupPayload payload = userService.removeUser(userDto);
        if (payload.getUserId() == 0L) {
            return new BaseResponse(BaseResponseStatus.FAIL_SIGNIN_NOT_EXIST);
        }
        else if (payload.getUserId() == -1L) {
            return new BaseResponse(BaseResponseStatus.FAIL_SIGNIN_NOT_MATCH_PW);
        }
        else {
            return new BaseResponse(payload);
        }
    }
}
