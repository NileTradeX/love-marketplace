package com.love.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.goods.entity.Label;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LabelMapper extends BaseMapper<Label> {

    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Select("insert into love.m_label(name, type, status) values(#{label.name}, #{label.type}, #{label.status}) ON CONFLICT (name, type) DO UPDATE SET name = excluded.name returning id")
    Long insertOrReturnId(@Param("label") Label label);
}
