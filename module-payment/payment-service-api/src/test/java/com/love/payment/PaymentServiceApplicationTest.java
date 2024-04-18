package com.love.payment;

import com.love.PaymentServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PaymentServiceApplication.class)
public class PaymentServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
