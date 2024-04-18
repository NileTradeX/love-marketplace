package com.love.review.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("r_review")
public class Review implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Integer rating;
    private Long pid;
    private Long userId;
    private Long merchantId;
    private Integer type;
    private Long relatedId;
    private String relatedStr;
    private Integer auditStatus;
    private String auditComment;
    private LocalDateTime auditTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic(delval = "1", value = "0")
    @TableField("deleted")
    private Integer deleted;
}
