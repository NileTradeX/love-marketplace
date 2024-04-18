package com.love.influencer;

import com.love.InfluencerServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = InfluencerServiceApplication.class)
public class InfluencerServiceApplicationTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }
}
