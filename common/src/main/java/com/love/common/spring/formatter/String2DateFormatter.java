package com.love.common.spring.formatter;

import com.love.common.Constants;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class String2DateFormatter implements Formatter<Date> {


    @Override
    @NonNull
    public String print(@NonNull Date date, @NonNull Locale locale) {
        return new SimpleDateFormat(Constants.DATE_TIME_FORMAT).format(date);
    }

    @Override
    @NonNull
    public Date parse(String text, @NonNull Locale locale) throws ParseException {
        SimpleDateFormat format;
        if (text.contains("CST")) {
            format = new SimpleDateFormat(Constants.DATE_FORMAT_CST, Locale.US);
        } else if (text.contains("T")) {
            format = new SimpleDateFormat(Constants.DATE_FORMAT_T);
        } else if (text.length() > 10) {
            format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        } else {
            format = new SimpleDateFormat(Constants.DATE_FORMAT);
        }

        return format.parse(text);
    }
}
