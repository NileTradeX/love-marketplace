package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("mer_user")
public class MerUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String account;
    private String username;
    private String password;
    private Integer status;
    private Integer type;
    private Long groupId;
    private String reason;
    private String uid;
    private LocalDateTime lastLoginTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic(delval = "1", value = "0")
    @TableField("deleted")
    private Integer deleted;
}
