package com.love.mq.config;

import com.alibaba.fastjson.TypeReference;
import com.love.common.exception.BizException;
import com.love.common.result.Result;
import com.love.common.util.FastjsonUtil;
import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

@Configuration
public class OpenfeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Component
    public static class SimpleFeignDecoder implements Decoder {

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
                throw BizException.build(result.getCode(), result.getMsg());
            }
        }
    }
}
