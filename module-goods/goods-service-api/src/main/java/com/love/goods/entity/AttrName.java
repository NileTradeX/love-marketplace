package com.love.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("m_attr_name")
public class AttrName implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
}
