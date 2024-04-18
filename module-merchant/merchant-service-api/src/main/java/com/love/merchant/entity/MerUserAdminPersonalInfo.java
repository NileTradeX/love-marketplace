package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("mer_user_admin_personal_info")
public class MerUserAdminPersonalInfo implements Serializable {
    @TableId(type = IdType.INPUT)
    private Long adminId;
    private String firstName;
    private String lastName;
    private String title;
    private Date birthday;
    private String phoneNumber;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
