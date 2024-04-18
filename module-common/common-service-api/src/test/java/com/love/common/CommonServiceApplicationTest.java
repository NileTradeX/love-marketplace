package com.love.common;

import com.love.CommonServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CommonServiceApplication.class)
public class CommonServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
