package com.love.order;

import com.love.OrderServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OrderServiceApplication.class)
public class OrderServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
