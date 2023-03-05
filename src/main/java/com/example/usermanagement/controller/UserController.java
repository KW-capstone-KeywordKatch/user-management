package com.example.usermanagement.controller;

import com.example.usermanagement.dto.request.UserDrop;
import com.example.usermanagement.dto.request.UserSignup;
import com.example.usermanagement.persistence.entity.User;
import com.example.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/signup")
    public String test(){
        System.out.println("UserController.test");
        return "/signupForm";
    }

    @PostMapping("/user/signup")
    public String createUser(UserSignup userSignup) {
        User user = new User();
        user.setEmail(userSignup.getEmail());
        user.setPassword(userSignup.getPassword());
        user.setAge(userSignup.getAge());
        user.setNickname(userSignup.getNickname());

        userService.joinUser(user);
        System.out.println("UserController.createUser");

        return "redirect:/";
    }

    @GetMapping("/user/duplication/{nickname}")
    public String nicknameCheck(@PathVariable("nickname") String nickname){
        Long checkId = userService.checkDuplicateNickname(nickname);
        if (checkId == 0L) {
            System.out.println("check OK");
            return "checkOK";
        }
        else {
            System.out.println("Duplicate");
            return "Duplicate";
        }
    }

    /**
     * email, password 를 입력했을 때 삭제
     * 입력한 email이 없으면 fail
     * 해당 email의 password가 일치하는 지는 고려안한 상태
     */
    @DeleteMapping("/user/drop")
    public String dropUser(UserDrop userDrop) {
        Long dropedId = userService.removeUser(userDrop);
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
