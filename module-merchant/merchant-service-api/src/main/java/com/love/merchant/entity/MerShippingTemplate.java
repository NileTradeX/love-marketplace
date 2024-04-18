package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("mer_shipping_template")
public class MerShippingTemplate implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long merchantId;
    private Integer shippingModels;
    private String setting;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
