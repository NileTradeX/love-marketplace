package com.love;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.love.merchant.backend.model.param.MerUserAdminSaveParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@SpringBootTest(classes = MerchantBackendApplication.class)
public class MerchantBackendApplicationMvcTest {

    static {
        System.setProperty("spring.profiles.active", "local");
    }

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test() throws Exception {

        String url = "/merchant/register";

        MerUserAdminSaveParam merUserAdminSaveParam = new MerUserAdminSaveParam();
        merUserAdminSaveParam.setAccount("evan@love.com");
        merUserAdminSaveParam.setCode("123456");
        merUserAdminSaveParam.setPassword("88998899");
        merUserAdminSaveParam.setBizName("weiyu");

        MerUserAdminSaveParam.PersonalInfo personalInfo = new MerUserAdminSaveParam.PersonalInfo();
        personalInfo.setFirstName("evan");
        personalInfo.setLastName("chen");
        personalInfo.setTitle("eng");
        personalInfo.setPhoneNumber("1345566");
        personalInfo.setBirthday(new Date());
        merUserAdminSaveParam.setPersonalInfo(personalInfo);

        MerUserAdminSaveParam.BusinessInfo businessInfo = new MerUserAdminSaveParam.BusinessInfo();
        businessInfo.setBizName("weiyu");
        businessInfo.setBizType(1);
        businessInfo.setOwnership(1);
        businessInfo.setZipCode("999");
        businessInfo.setCity("us");
        businessInfo.setState("CXX");
        businessInfo.setBizPhoneNumber("12455");
        businessInfo.setWebsite("https://xxx.com");
        businessInfo.setAddress("xxx");
        businessInfo.setIncorDate(new Date());
        businessInfo.setCountry("us");
        merUserAdminSaveParam.setBusinessInfo(businessInfo);

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(merUserAdminSaveParam))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        String contentAsString = response.getContentAsString();
        System.err.println(status);
        System.err.println(contentAsString);
    }
}
