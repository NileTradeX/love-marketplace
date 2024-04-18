package com.love.marketplace.utils;

public class IdUtil {
    private IdUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static String getNextOrderNo() {
        String orderNo = cn.hutool.core.util.IdUtil.getSnowflakeNextIdStr();
        return orderNo.substring(10, 15) + orderNo.substring(0, 5) + orderNo.substring(5, 10) + orderNo.substring(15);
    }
}
