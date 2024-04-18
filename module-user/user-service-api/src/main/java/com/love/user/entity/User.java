package com.love.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("u_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String avatar;
    private Integer status;
    private LocalDateTime lastLoginTime;
    private String notes;
    private String uid;
    private Integer source;
    private Integer acceptTermsOfService;
    private Integer subscribeToNewsletter;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic(value = "0", delval = "id")
    @TableField("deleted")
    private Integer deleted;
}

