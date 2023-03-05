package com.example.usermanagement.duplicate;

import com.example.usermanagement.UserConfig;
import com.example.usermanagement.persistence.entity.User;
import com.example.usermanagement.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DuplicateTest {


    @Test
    void duplicateTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(UserConfig.class);

        UserService userService = ac.getBean(UserService.class);

        User user1 = new User();
        user1.setNickname("duplicateTest");
        user1.setEmail("test@naver.com");
        user1.setPassword("12345");

        userService.signupUser(user1);

        Long duplicateNickname = userService.checkDuplicateNickname("duplicateTest");

        Assertions.assertThat(duplicateNickname).isNotEqualTo(0L);

        Long notDuplicateNickname = userService.checkDuplicateNickname("notDuplicateNickname");
        Assertions.assertThat(notDuplicateNickname).isEqualTo(0L);
    }
}
