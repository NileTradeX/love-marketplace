package com.love.order.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

public class AfterSaleUtil {

    public static String getAfterSaleNo() {
        String no = IdWorker.getIdStr();
        return no.substring(10, 15) + no.substring(0, 5) + no.substring(5, 10) + no.substring(15);
    }

    public static String getRefundNo() {
        String no = IdWorker.getIdStr();
        return no.substring(10, 15) + no.substring(0, 5) + no.substring(5, 10) + no.substring(15);
    }
}
