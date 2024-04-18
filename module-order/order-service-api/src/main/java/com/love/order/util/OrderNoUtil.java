package com.love.order.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

public class OrderNoUtil {

    public static String nextId() {
        String orderNo = IdWorker.getIdStr();
        return orderNo.substring(10, 15) + orderNo.substring(0, 5) + orderNo.substring(5, 10) + orderNo.substring(15);
    }
}
