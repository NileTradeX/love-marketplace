package com.love.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.goods.entity.AttrValue;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AttrValueMapper extends BaseMapper<AttrValue> {
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Select("insert into love.m_attr_value(attr_name_id, value) values(#{attrValue.attrNameId}, #{attrValue.value}) ON CONFLICT (attr_name_id, value) DO UPDATE SET value = excluded.value returning id")
    Long insertOrReturnId(@Param("attrValue") AttrValue attrValue);
}
