package com.love.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("m_goods_doc")
public class GoodsDoc implements Serializable {
    @TableId(type = IdType.INPUT)
    private Long goodsId;
    private String coa;
    private Integer coaStatus;
    private String msds;
    private Integer msdsStatus;
    private String ppp;
    private Integer pppStatus;
    private String logfm;
    private Integer logfmStatus;
    private String rein;
    private Integer reinStatus;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

