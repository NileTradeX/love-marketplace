package com.love.review;

import com.love.ReviewServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = ReviewServiceApplication.class)
public class ReviewServiceApplicationMvcTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test() throws Exception {

    }
}
