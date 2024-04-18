package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("mer_user_admin_business_info")
public class MerUserAdminBusinessInfo implements Serializable {
    @TableId(type = IdType.INPUT)
    private Long adminId;
    private String bizName;
    private Integer bizType;
    private Integer ownership;
    private Date incorDate;
    private String website;
    private String bizPhoneNumber;
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String address;
    private String bizOrderMgmtEmail;
    private String defaultCarrier;
    private Integer bizSize;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
