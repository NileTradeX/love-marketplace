package com.love.common.spring.formatter;

import com.love.common.Constants;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class String2LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    @NonNull
    @Override
    public String print(@NonNull LocalDateTime datetime, @NonNull Locale locale) {
        return datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @NonNull
    public LocalDateTime parse(@NonNull String text, @NonNull Locale locale) {
        if (text.contains("CST")) {
            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_CST, Locale.US));
        } else if (text.contains("T")) {
            return LocalDateTime.parse(text);
        } else if (text.length() > 10) {
            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT));
        } else {
            return LocalDate.parse(text, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)).atTime(LocalTime.parse("00:00:00"));
        }
    }
}
