package com.love.review;

import com.love.ShipmentServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShipmentServiceApplication.class)
public class ShipmentServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
