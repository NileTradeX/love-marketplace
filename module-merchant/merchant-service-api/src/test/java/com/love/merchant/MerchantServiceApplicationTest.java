package com.love.merchant;

import com.love.MerchantServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MerchantServiceApplication.class)
public class MerchantServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
