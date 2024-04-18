package com.love.influencer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.influencer.entity.InfUserOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface InfUserOrderMapper extends BaseMapper<InfUserOrder> {
    @Select("<script> " +
            "select coalesce(sum(commission),0.0) as balance " +
            "from inf_user_order iu " +
            "left join o_order_item oi " +
            "on iu.order_id=oi.order_id and iu.sku_id=oi.sku_id " +
            "where iu.influencer_id=#{influencerId} " +
            "and oi.delivery_time is not null " +
            "and oi.delivery_time  &lt; now() - interval '30 d' "+
            "</script>")
    BigDecimal calBalance(@Param("influencerId") Long influencerId);

    @Update(
            "<script> " +
                    "update inf_user_order " +
                    "set refund_amount=#{refundAmount}, " +
                    "commission=(total_amount-#{refundAmount})::float*mer_commission_rate/100*commission_rate/100 " +
                    "where order_id=#{orderId} " +
                    "and sku_id=#{skuId} " +
            "</script>"
    )
    Long refund(@Param("orderId") Long orderId, @Param("skuId") Long skuId, @Param("refundAmount") BigDecimal refundAmount);
}
