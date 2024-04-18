package com.love.rbac;

import com.love.RbacServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RbacServiceApplication.class)
public class RbacServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
