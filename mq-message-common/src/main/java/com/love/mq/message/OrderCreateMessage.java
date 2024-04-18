package com.love.mq.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class OrderCreateMessage implements Serializable {
    private String orderNo;
    private Map<Long, Integer> sukQtyMap;
}
