package com.love.influencer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("inf_user_address")
public class InfUserAddress implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long influencerId;
    private String country;
    private String phoneNumber;
    private String city;
    private String state;
    private String zipCode;
    private String address;
    private Integer isDefault;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
