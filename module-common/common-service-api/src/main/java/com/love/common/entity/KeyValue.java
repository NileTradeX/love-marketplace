package com.love.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("d_key_value")
public class KeyValue implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String key;
    private String value;
    private String remark;
    private Integer timeless;
    private Date beginTime;
    private Date endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
