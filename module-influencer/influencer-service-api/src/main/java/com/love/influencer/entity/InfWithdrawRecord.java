package com.love.influencer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inf_user_withdraw_record")
public class InfWithdrawRecord implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long influencerId;
    private BigDecimal amount;
    private String paymentId;
    private LocalDateTime payTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
