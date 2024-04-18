package com.love.common.spring.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class SimpleDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        if (Objects.nonNull(value)) {
            generator.writeString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
        } else {
            generator.writeNull();
        }
    }
}
