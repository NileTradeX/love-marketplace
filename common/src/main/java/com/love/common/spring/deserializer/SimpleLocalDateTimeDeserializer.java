package com.love.common.spring.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.love.common.Constants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SimpleLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        String text = parser.getText();
        if (Objects.isNull(text) || text.trim().isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter;
        if (text.contains("CST")) {
            formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_CST);
        } else if (text.length() > 10) {
            formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
        } else {
            formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
            return LocalDate.parse(text, formatter).atTime(LocalTime.parse("00:00:00"));
        }
        return LocalDateTime.parse(text, formatter);
    }
}
