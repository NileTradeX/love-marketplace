package com.love.influencer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("inf_store")
public class InfStore implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String cover;
    private String description;
    private String displayName;
    private Integer goodsSortType;
    private Long influencerId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
