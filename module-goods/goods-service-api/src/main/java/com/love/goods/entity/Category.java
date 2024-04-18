package com.love.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("m_category")
public class Category implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long pid;
    private String name;
    private String alias;
    private String icon;
    private Integer level;
    private Integer sortNum;
    private Integer status;
    private Integer type;
    private String ids;
}
