package com.love.mq.cousumer.goods;

import com.love.goods.bo.GoodsDetailQueryBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.dto.BrandDTO;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.GoodsDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.goods.enums.GoodsStatus;
import com.love.influencer.bo.InfGoodsUpdateByIdBO;
import com.love.influencer.client.InfGoodsFeignClient;
import com.love.mq.constants.RabbitMQConstants;
import com.love.mq.enums.GoodsOperationType;
import com.love.mq.message.GoodsUpdateMessage;
import com.love.mq.model.AlgoliaIndexUpdateDTO;
import com.love.mq.service.goods.GoodsAlgoliaIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AlgoliaIndexUpdateMessageReceiver {

    private final GoodsAlgoliaIndexService goodsAlgoliaIndexService;

    private final GoodsFeignClient goodsFeignClient;

    private final InfGoodsFeignClient influencerGoodsFeignClient;

    @RabbitListener(
            id = "fanout#1",
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitMQConstants.GOODS_UPDATE_ALGOLIA_QUEUE, durable = "true"),
                    exchange = @Exchange(name = RabbitMQConstants.GOODS_UPDATE_EXCHANGE, type = ExchangeTypes.FANOUT)
            ),
            errorHandler = "rabbitListenerErrorHandler"
    )
    public void receiveGoodsUpdate(@Payload GoodsUpdateMessage goodsUpdateMessage) {
        if (GoodsOperationType.UPDATE == goodsUpdateMessage.getGoodsOperationType()) {
            GoodsDTO goods = goodsFeignClient.detail(GoodsDetailQueryBO.builder().id(goodsUpdateMessage.getId()).build());

            List<GoodsSkuDTO> skuList = goods.getSkus();
            BigDecimal max = skuList.stream().map(GoodsSkuDTO::getPrice).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
            BigDecimal min = skuList.stream().map(GoodsSkuDTO::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

            CategoryDTO firstCate = goods.getFirstCategory();
            CategoryDTO secondCate = goods.getSecondCategory();
            BrandDTO brand = goods.getBrand();

            AlgoliaIndexUpdateDTO algoliaIndexUpdateDTO = AlgoliaIndexUpdateDTO.builder()
                    .id(goods.getId())
                    .objectID(String.valueOf(goods.getId()))
                    .intro(goods.getIntro())
                    .communityScore(goods.getCommunityScore())
                    .loveScore(goods.getLoveScore())
                    .descWarnings(goods.getDescWarnings())
                    .descText(goods.getDescText())
                    .maxPrice(max.setScale(2, RoundingMode.HALF_DOWN))
                    .minPrice(min.setScale(2, RoundingMode.HALF_DOWN))
                    .subTitle(goods.getSubTitle())
                    .title(goods.getTitle())
                    .whiteBgImg(goods.getWhiteBgImg())
                    .slug(goods.getSlug())
                    .firstCateId(firstCate.getId())
                    .firstCateName(firstCate.getName())
                    .secondCateId(secondCate.getId())
                    .secondCateName(secondCate.getName())
                    .brandId(brand.getId())
                    .brandName(brand.getName())
                    .updateTime(new Date().getTime())
                    .salesVolume(goods.getSalesVolume())
                    .build();
            goodsAlgoliaIndexService.algoliaIndexUpdate(algoliaIndexUpdateDTO);
            influencerGoodsFeignClient.updateById(InfGoodsUpdateByIdBO.builder().goodsStatus(GoodsStatus.ON_SALES.getStatus()).maxPrice(max).minPrice(min).communityScore(goods.getCommunityScore()).goodsId(goodsUpdateMessage.getId()).build());
        } else if (GoodsOperationType.DELETE == goodsUpdateMessage.getGoodsOperationType()) {
            goodsAlgoliaIndexService.algoliaIndexDelete(String.valueOf(goodsUpdateMessage.getId()));
            influencerGoodsFeignClient.updateById(InfGoodsUpdateByIdBO.builder().goodsStatus(GoodsStatus.DELISTED.getStatus()).status(com.love.influencer.enums.GoodsStatus.INVALID.getStatus()).goodsId(goodsUpdateMessage.getId()).build());
        }

    }
}
