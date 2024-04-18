package com.love.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {

    private static final int DEFAULT_DIV_SCALE = 4;

    public static BigDecimal add(String factor1, String factor2) {
        BigDecimal m = new BigDecimal(factor1);
        BigDecimal n = new BigDecimal(factor2);
        return m.add(n);
    }

    public static BigDecimal add(Number factor1, Number factor2) {
        return add(factor1.toString(), factor2.toString());
    }

    public static BigDecimal subtract(String factor1, String factor2) {
        BigDecimal m = new BigDecimal(factor1);
        BigDecimal n = new BigDecimal(factor2);
        return m.subtract(n);
    }

    public static BigDecimal subtract(Number factor1, Number factor2) {
        return subtract(factor1.toString(), factor2.toString());
    }

    public static BigDecimal multiply(String factor1, String factor2) {
        BigDecimal m = new BigDecimal(factor1);
        BigDecimal n = new BigDecimal(factor2);
        return m.multiply(n);
    }

    public static BigDecimal multiply(Number factor1, Number factor2) {
        return multiply(factor1.toString(), factor2.toString());
    }

    public static BigDecimal multiply_100(Number factor) {
        return multiply(factor.toString(), "100");
    }

    public static BigDecimal multiply_100(String factor) {
        return multiply(factor, "100");
    }

    public static BigDecimal divide(String factor1, String factor2, int scale, RoundingMode round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal m = new BigDecimal(factor1);
        BigDecimal n = new BigDecimal(factor2);
        return m.divide(n, scale, round_mode);
    }

    public static BigDecimal divide(Number factor1, Number factor2, int scale, RoundingMode round_mode) {
        return divide(factor1.toString(), factor2.toString(), scale, round_mode);
    }

    public static BigDecimal divide(String factor1, String factor2, int scale) {
        return divide(factor1, factor2, scale, RoundingMode.HALF_DOWN);
    }


    public static BigDecimal divide(Number factor1, Number factor2, int scale) {
        return divide(factor1.toString(), factor2.toString(), scale);
    }

    public static BigDecimal divide(String factor1, String factor2) {
        return divide(factor1, factor2, DEFAULT_DIV_SCALE, RoundingMode.HALF_DOWN);
    }


    public static BigDecimal divide(Number factor1, Number factor2) {
        return divide(factor1.toString(), factor2.toString());
    }

    public static BigDecimal round(String v, int scale, RoundingMode round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, round_mode);
    }

    public static BigDecimal round(Number v, int scale, RoundingMode round_mode) {
        return round(v.toString(), scale, round_mode);
    }

    public static BigDecimal round(String v, int scale) {
        return round(v, scale, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal commonRound(String v) {
        return round(v, 2);
    }

    public static String commonRound(BigDecimal v) {
        return v.setScale(2, RoundingMode.HALF_DOWN).toString();
    }

    public static String rand(int min, int max, boolean flag) {
        int ret = ThreadLocalRandom.current().nextInt(max - min) + min;
        if (flag) {
            return String.format("%0" + String.valueOf(max).length() + "d", ret);
        }
        return ret + "";
    }
}
