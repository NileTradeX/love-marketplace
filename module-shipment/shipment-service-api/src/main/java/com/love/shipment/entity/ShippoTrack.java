package com.love.shipment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("shippo_track")
public class ShippoTrack implements Serializable {
    @TableId(type = IdType.INPUT)
    private String trackingNo;
    private String trackingInfo;
    private String carriers;
    private Long merchantOrderId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
