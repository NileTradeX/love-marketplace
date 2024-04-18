package com.love.influencer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inf_user")
public class InfUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String account;
    private String username;
    private String password;
    private String avatar;
    private String firstName;
    private String lastName;
    private String generalIntroduction;
    private String socialLinks;
    private String code;
    private Integer status;
    private String paypalAccount;
    private BigDecimal commissionRate;
    private LocalDateTime lastLoginTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
