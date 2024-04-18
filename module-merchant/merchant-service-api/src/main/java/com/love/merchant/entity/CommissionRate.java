package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mer_user_admin_commission_fee_rate")
public class CommissionRate implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long adminId;
    private String bizName;
    private BigDecimal rate;
    private LocalDateTime effectiveTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
