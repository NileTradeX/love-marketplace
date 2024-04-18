package com.love.review;

import com.love.ReviewServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ReviewServiceApplication.class)
public class ReviewServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
