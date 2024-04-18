package com.love.influencer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inf_user_goods")
public class InfGoods implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long goodsId;
    private Long influencerId;
    private Integer status;
    private Integer sortNum;
    private Integer salesVolume;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal commissionRate;
    private Integer communityScore;
    private Integer goodsStatus;
    private Integer availableStock;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
