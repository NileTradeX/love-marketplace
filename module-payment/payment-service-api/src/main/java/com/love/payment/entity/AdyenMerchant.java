package com.love.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pay_adyen_merchant")
public class AdyenMerchant implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long merchantId;
    private String legalEntityId;
    private String accountHolderId;
    private String balanceAccountId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
