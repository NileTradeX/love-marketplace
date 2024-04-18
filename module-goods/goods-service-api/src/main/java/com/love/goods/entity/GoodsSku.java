package com.love.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("m_goods_sku")
public class GoodsSku implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long merchantId;
    private Long goodsId;
    private String attrValueJson;
    private String cover;
    private String imgList;
    private BigDecimal price;
    private Integer onHandStock;
    private Integer availableStock;
    private Integer committedStock;
    private String code;
    private Integer status;
    private BigDecimal shippingWeight;
    private String shippingWeightUnit;
    private String gtin;
    private String mpn;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer deleted;
    private Integer defaultSku;
}
