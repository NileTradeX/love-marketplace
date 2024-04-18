package com.love.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.goods.entity.AttrName;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AttrNameMapper extends BaseMapper<AttrName> {

    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Select("insert into love.m_attr_name(name) values(#{attrName.name}) ON CONFLICT (name) DO UPDATE SET name = excluded.name returning id")
    Long insertOrReturnId(@Param("attrName") AttrName attrName);
}
