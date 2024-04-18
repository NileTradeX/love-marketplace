package com.love.goods;

import com.love.GoodsServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GoodsServiceApplication.class)
public class GoodsServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
