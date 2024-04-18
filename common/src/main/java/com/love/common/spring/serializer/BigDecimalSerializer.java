package com.love.common.spring.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.nonNull(value)) {
            generator.writeString(value.setScale(2, RoundingMode.HALF_DOWN).toString());
        } else {
            generator.writeNull();
        }
    }
}
