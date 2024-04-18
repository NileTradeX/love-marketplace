package com.love.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("r_resource")
public class Resource implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String oriName;
    private Long size;
    private String key;
    private String ext;
    private Integer type;
    private Integer width;
    private Integer height;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
