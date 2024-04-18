package com.love.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class DateUtil {

    private DateUtil(){

    }

    public static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
    }

    public static String format(LocalDateTime localDateTime) {
        return Objects.isNull(localDateTime) ? null : localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String offsetMinuteStr(String date, String format, int offset) {
        return cn.hutool.core.date.DateUtil.offsetMinute(cn.hutool.core.date.DateUtil.parse(date, format), offset).toString();
    }

    public static int compareDateTime(Date startTime, Date endTime) {
        if (startTime.getTime() > endTime.getTime()) {
            return 1;
        } else if (startTime.getTime() < endTime.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
