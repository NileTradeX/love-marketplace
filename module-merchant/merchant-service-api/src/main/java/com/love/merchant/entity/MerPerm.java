package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("mer_perm")
public class MerPerm implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long pid;
    private Integer type;
    private String code;
    private String icon;
    private String path;
    private Integer sortNum;
    private String apis;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
