package com.love.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.goods.bo.GoodsStatusPageQueryBO;
import com.love.goods.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface GoodsMapper extends BaseMapper<Goods> {
    @Select("SELECT * FROM ("
            + "SELECT g.id, g.min_price, g.max_price, g.white_bg_img, g.sub_title, g.title, g.love_score, g.community_score, SUM(s.available_stock) AS available_stock, g.sales_volume "
            + "FROM m_goods g "
            + "JOIN m_goods_sku s ON g.id = s.goods_id "
            + "WHERE g.status = #{goodsStatusPageQueryBO.status} "
            + "AND g.deleted = 0 "
            + "GROUP BY g.id"
            + ") r "
            + "WHERE r.available_stock > 0 order by r.sales_volume desc limit #{goodsStatusPageQueryBO.limitNum}")
    List<Map<String, Object>> getPopular(@Param("goodsStatusPageQueryBO") GoodsStatusPageQueryBO goodsStatusPageQueryBO);
}