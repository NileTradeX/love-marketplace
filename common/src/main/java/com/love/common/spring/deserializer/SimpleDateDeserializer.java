package com.love.common.spring.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.love.common.Constants;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SimpleDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        String text = parser.getText();
        if (Objects.isNull(text) || text.trim().isEmpty()) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat();
        if (text.contains("CST")) {
            format.applyPattern(Constants.DATE_FORMAT_CST);
        } else if (text.length() > 10) {
            format.applyPattern(Constants.DATE_TIME_FORMAT);
        } else {
            format.applyPattern(Constants.DATE_FORMAT);
        }

        try {
            return format.parse(text.trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
