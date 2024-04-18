package com.love.mq.constants;

public interface RabbitMQConstants {

    String OD_BIZ_EXCHANGE = "order.delay.biz.direct.exchange";
    String OD_BIZ_QUEUE = "order.delay.biz.queue";
    String OD_BIZ_KEY = "order.delay.biz.key";

    String OD_DLX_NAME = "order.delay.dl.exchange";
    String OD_DLQ_NAME = "order.delay.dl.queue";
    String OD_DL_RK_NAME = "order.delay.dl.routing.key";

    String GOODS_UPDATE_EXCHANGE = "goods.update.fanout.exchange";
    String GOODS_UPDATE_GOOGLE_ADS_QUEUE = "goods.update.google_ads.queue";
    String GOODS_UPDATE_ALGOLIA_QUEUE = "goods.update.algolia.queue";
    String GOODS_UPDATE_INFLUENCER_QUEUE = "goods.update.influencer.queue";

}
