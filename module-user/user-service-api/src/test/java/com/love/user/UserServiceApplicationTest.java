package com.love.user;

import com.love.UserServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserServiceApplication.class)
public class UserServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
