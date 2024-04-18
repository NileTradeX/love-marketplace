package com.love.common.util;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RandomUtil {

    public static final String NUMBERS = "0123456789";
    public static final String LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int NUMBERS_LEN = NUMBERS.length();
    private static final int LETTERS_LEN = LETTERS.length();


    public RandomUtil() {
    }

    private static String timestamp() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    public static String randomStr(int length) {
        return randomStr(length, false);
    }

    public static String randomStr(int length, boolean useTimestamp) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(LETTERS.charAt(secureRandom.nextInt(LETTERS_LEN)));
        }

        return useTimestamp ? timestamp() + builder : builder.toString();
    }

    public static String randomNumStr(int length) {
        return randomNumStr(length, false);
    }


    public static String randomNumStr(int length, boolean useTimestamp) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(NUMBERS.charAt(secureRandom.nextInt(NUMBERS_LEN)));
        }

        return useTimestamp ? timestamp() + builder : builder.toString();
    }


    public static int randomNum(int min, int max) {
        return secureRandom.nextInt(max - min + 1) + min;
    }
}
