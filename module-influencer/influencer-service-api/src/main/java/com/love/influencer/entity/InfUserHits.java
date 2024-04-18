package com.love.influencer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("inf_user_hits")
public class InfUserHits implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long influencerId;
    private String ip;
    private LocalDateTime createTime;
}
