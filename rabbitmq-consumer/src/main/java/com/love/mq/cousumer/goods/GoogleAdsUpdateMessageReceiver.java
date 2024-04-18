package com.love.mq.cousumer.goods;

import com.google.api.services.content.ShoppingContent;
import com.google.api.services.content.model.Price;
import com.google.api.services.content.model.Product;
import com.love.common.Constants;
import com.love.goods.bo.GoodsDetailQueryBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.dto.GoodsDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.mq.constants.RabbitMQConstants;
import com.love.mq.enums.GoodsOperationType;
import com.love.mq.message.GoodsUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;

import static com.love.common.Constants.GOODS_URL_PREFIX;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoogleAdsUpdateMessageReceiver {

    private final ShoppingContent shoppingContentService;

    private final GoodsFeignClient goodsFeignClient;

    @Value("${spring.profiles.active:local}")
    private String profile;

    @Value("${google.merchant-id:749188311}")
    private BigInteger merchantId;

    @RabbitListener(
            id = "fanout#2",
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitMQConstants.GOODS_UPDATE_GOOGLE_ADS_QUEUE, durable = "true"),
                    exchange = @Exchange(name = RabbitMQConstants.GOODS_UPDATE_EXCHANGE, type = ExchangeTypes.FANOUT)
            ),
            errorHandler = "rabbitListenerErrorHandler"
    )
    public void receiveGoodsUpdate(@Payload GoodsUpdateMessage goodsUpdateMessage) {
        if (GoodsOperationType.UPDATE == goodsUpdateMessage.getGoodsOperationType()) {
            GoodsDTO goodsDTO = goodsFeignClient.detail(GoodsDetailQueryBO.builder().id(goodsUpdateMessage.getId()).build());
            Product product = new Product();
            product.setOfferId(String.valueOf(goodsDTO.getId()));
            product.setTitle(goodsDTO.getSearchPageTitle());
            product.setDescription(goodsDTO.getSearchMetaDescription());
            product.setLink(GOODS_URL_PREFIX + goodsDTO.getSlug());
            product.setImageLink(Constants.S3_HOST + goodsDTO.getWhiteBgImg());
            goodsDTO.getSkus().stream().filter(goodsSkuDTO -> goodsSkuDTO.getStatus() == 1).min(Comparator.comparing(GoodsSkuDTO::getPrice)).ifPresent(sku -> {
                Price price = new Price().setCurrency("USD").setValue(sku.getPrice().toString());
                product.setPrice(price);
                product.setGtin(sku.getGtin());
                product.setMpn(sku.getMpn());
            });
            product.setBrand(goodsDTO.getBrand().getName());
            product.setContentLanguage("en");
            product.setTargetCountry("US");
            product.setChannel("online");
            product.setAvailability("in stock");
            product.setCondition("new");
            log.info("Upload products to Google Merchant. The uploaded product information is : {} , env: {} ", product, profile);
            if ("prod".equalsIgnoreCase(profile)) {
                try {
                    Product result = shoppingContentService.products().insert(merchantId, product).execute();
                    log.info("The product is uploaded successfully and the product information after upload is : {}", result);
                } catch (IOException e) {
                    log.error("Uploading products to Google Merchant failed.", e);
                }
            }
        } else if (GoodsOperationType.DELETE == goodsUpdateMessage.getGoodsOperationType()) {
            if ("prod".equalsIgnoreCase(profile)) {
                try {
                    String productId = "online:en:US:" + goodsUpdateMessage.getId();
                    shoppingContentService.products().delete(merchantId, productId).execute();
                    log.info("Deleting product [{}] from the Google Merchant was successful ", productId);
                } catch (IOException e) {
                    log.error("Deleting product from Google Merchant failed.", e);
                }
            }
        }
    }
}
