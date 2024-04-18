package com.love.api.common.config;

import com.alibaba.fastjson.TypeReference;
import com.love.common.exception.BizException;
import com.love.common.result.Result;
import com.love.common.util.FastjsonUtil;
import feign.*;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Enumeration;

@Configuration
public class OpenfeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Component
    public static class FeignRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            ServletRequestAttributes attributes = ( ServletRequestAttributes ) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        // 跳过 content-length, 防止RPC时参数长度与请求时不一致
                        if ("content-length".equalsIgnoreCase(name)) {
                            continue;
                        }
                        template.header(name, values);
                    }
                }
            }
        }
    }

    @Component
    public static class FeignClientResponseInterceptor implements Decoder {

        private TypeReference<Result<String>> resultType() {
            return new TypeReference<Result<String>>() {
            };
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            String data = Util.toString(response.body().asReader(Util.UTF_8));
            Result<String> result = FastjsonUtil.json2bean(data, resultType());
            if (result.isSuccess()) {
                return FastjsonUtil.json2bean(result.getData(), type);
            } else {
                throw BizException.buildWithCode(result.getCode(), result.getMsg());
            }
        }
    }
}
