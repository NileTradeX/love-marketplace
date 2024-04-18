package com.love.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("m_label")
public class Label implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    /**
     * 0: ingredient, 1: benefit
     */
    private Integer type;
    private Integer status;
}
