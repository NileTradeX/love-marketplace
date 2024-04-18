package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mer_user_admin_invitation")
public class MerUserAdminInvitation implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String bizName;
    private String email;
    private String code;
    private Integer status;
    private BigDecimal commissionFeeRate;
    private String mpa;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
