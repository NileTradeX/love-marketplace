package com.love.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("u_address")
public class UserAddress implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String state;
    private String zipCode;
    private String company;
    private String address;
    private Integer isDefault;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic(value = "0", delval = "id")
    @TableField("deleted")
    private Integer deleted;
}

