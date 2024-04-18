package com.love.mq.cousumer.goods;

import com.love.common.param.IdParam;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.influencer.bo.InfGoodsStockUpdateBO;
import com.love.influencer.client.InfGoodsFeignClient;
import com.love.mq.constants.RabbitMQConstants;
import com.love.mq.message.GoodsUpdateMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfluencerGoodsStockUpdateMessageReceiver {

    private final GoodsSkuFeignClient goodsSkuFeignClient;

    private final InfGoodsFeignClient influencerGoodsFeignClient;

    @RabbitListener(
            id = "fanout#3",
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitMQConstants.GOODS_UPDATE_INFLUENCER_QUEUE, durable = "true"),
                    exchange = @Exchange(name = RabbitMQConstants.GOODS_UPDATE_EXCHANGE, type = ExchangeTypes.FANOUT)
            ),
            errorHandler = "rabbitListenerErrorHandler"
    )
    public void receiveGoodsUpdate(@Payload GoodsUpdateMessage goodsUpdateMessage) {
        Integer availableStock = goodsSkuFeignClient.queryAvailableStockByGoodsId(IdParam.builder().id(goodsUpdateMessage.getId()).build());
        if (Objects.nonNull(availableStock)) {
            influencerGoodsFeignClient.updateGoodsAvailableStock(InfGoodsStockUpdateBO.builder().availableStock(availableStock).goodsId(goodsUpdateMessage.getId()).build());
        }
    }
}
