package com.example.usermanagement.signup;

import com.example.usermanagement.persistence.dao.UserDao;
import com.example.usermanagement.persistence.dao.UserDaoTest;
import com.example.usermanagement.persistence.entity.User;
import com.example.usermanagement.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

public class SignupTest {



    UserDao userDao = new UserDaoTest();
    UserService userService = new UserService(userDao);

    @Test
    void saveTest() {
        User user = new User();
        user.setEmail("test@naver.com");
        user.setNickname("user");
        user.setPassword("abc123!");

        userService.signupUser(user);

        User findUser = userDao.findByNickname("user").get();
        System.out.println("findUser = " + findUser);
        Assertions.assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
    }

    @Test
    void beanTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(UserConfig.class);

        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("@@@@name = " + beanDefinitionName + "bean = " + bean);
            }
        }
    }

    @Configuration
    @ComponentScan
    public class UserConfig {
    }
}
