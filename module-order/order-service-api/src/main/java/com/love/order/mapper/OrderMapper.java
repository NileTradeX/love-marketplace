package com.love.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.order.bo.OrderQueryPageBO;
import com.love.order.entity.Order;
import com.love.order.query.CountByUserIdAndGoodsIdQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    @Select("<script>  " +
            "select distinct(oi.merchant_order_id)" +
            " from o_order o " +
            " left join o_merchant_order mo on o.id = mo.order_id " +
            " left join o_order_item oi on oi.merchant_order_id = mo.id  " +
            "<where> " +
            "<if test='param.consignee!=null'>and o.consignee like '%${param.consignee}%'</if> " +
            "<if test='param.consigneePhone!=null'>and o.consignee_phone like '%${param.consigneePhone}%'</if> " +
            "<if test='param.merchantId!=null'>and mo.merchant_id=#{param.merchantId}</if> " +
            "<if test='param.orderNo!=null'>and mo.mer_order_no=#{param.orderNo}</if> " +
            "<if test='param.trackingNo!=null'>and oi.tracking_no=#{param.trackingNo}</if> " +
            "<if test='param.status!=null'>and mo.status=#{param.status}</if> " +
            "<if test='param.goodsId!=null'>and oi.goods_id=#{param.goodsId}</if> " +
            "<if test='param.goodsTitle!=null'>and oi.goods_title like '%${param.goodsTitle}%'</if> " +
            "<if test='param.skuId!=null'>and oi.sku_id=#{param.skuId}</if> " +
            "<if test='param.beginTime!=null'>and oi.create_time &gt; #{param.beginTime}</if> " +
            "<if test='param.endTime!=null'>and oi.create_time &lt; #{param.endTime}</if> " +
            "</where> order by oi.merchant_order_id desc" +
            " limit  #{param.pageSize} offset #{param.pageSize}*(#{param.pageNum}-1)" +
            "</script>")
    List<Long> queryPage(@Param("param") OrderQueryPageBO param);

    @Select("<script> select count(*) from ( " +
            "select distinct(oi.merchant_order_id)" +
            " from o_order o " +
            " left join o_merchant_order mo on o.id = mo.order_id " +
            " left join o_order_item oi on oi.merchant_order_id = mo.id  " +
            "<where> " +
            "<if test='param.consignee!=null'>and o.consignee like '%${param.consignee}%'</if> " +
            "<if test='param.consigneePhone!=null'>and o.consignee_phone like '%${param.consigneePhone}%'</if> " +
            "<if test='param.merchantId!=null'>and mo.merchant_id=#{param.merchantId}</if> " +
            "<if test='param.orderNo!=null'>and mo.mer_order_no=#{param.orderNo}</if> " +
            "<if test='param.trackingNo!=null'>and oi.tracking_no=#{param.trackingNo}</if> " +
            "<if test='param.status!=null'>and mo.status=#{param.status}</if> " +
            "<if test='param.goodsId!=null'>and oi.goods_id=#{param.goodsId}</if> " +
            "<if test='param.goodsTitle!=null'>and oi.goods_title like '%${param.goodsTitle}%'</if> " +
            "<if test='param.skuId!=null'>and oi.sku_id=#{param.skuId}</if> " +
            "<if test='param.beginTime!=null'>and oi.create_time &gt;#{param.beginTime}</if> " +
            "<if test='param.endTime!=null'>and oi.create_time &lt; #{param.endTime}</if> " +
            "</where> ) t " +
            "</script>")
    long countForPage(@Param("param") OrderQueryPageBO param);
    @Select("<script> " +
            "select count(1) "+
            "from o_order o "+
            "left join o_order_item oi on oi.order_id=o.id "+
            "<where> " +
            "<if test='param.userId!=null'>and o.buyer_id=#{param.userId}</if> " +
            "<if test='param.goodsId!=null'>and oi.goods_id=#{param.goodsId}</if> " +
            "<if test='param.amount!=null'>and o.total_amount >= #{param.amount}</if> " +
            "<if test='param.beginTime!=null'>and o.create_time between #{param.beginTime} and #{param.endTime}</if> " +
            "and o.status not in (50)" +
            "</where> " +
            "</script>")
    long countByUserIdAndGoodsId(@Param("param") CountByUserIdAndGoodsIdQuery param);
}
