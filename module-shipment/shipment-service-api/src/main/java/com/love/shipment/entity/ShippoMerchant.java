package com.love.shipment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("shippo_merchant")
public class ShippoMerchant implements Serializable {
    @TableId(type = IdType.INPUT)
    private Long merchantId;
    private String accessToken;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
