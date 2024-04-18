package com.love.marketplace.manager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.love.common.enums.ExpireTime;
import com.love.common.util.RedisUtil;
import com.love.influencer.bo.InfStoreQueryByDisplayNameBO;
import com.love.influencer.bo.InfUserHitsSaveBO;
import com.love.influencer.client.InfStoreFeignClient;
import com.love.influencer.client.InfUserHitsFeignClient;
import com.love.influencer.dto.InfStoreDTO;
import com.love.marketplace.utils.IpUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfStoreManager {

    private final Logger logger = LoggerFactory.getLogger(InfStoreManager.class);

    private static final Integer INIT_COUNT = 1;

    private final RedisUtil redisUtil;
    private final InfStoreFeignClient infStoreFeignClient;
    private final InfUserHitsFeignClient infUserHitsFeignClient;

    public InfStoreDTO queryByDisplayName(InfStoreQueryByDisplayNameBO influencerStoreQueryBO, HttpServletRequest request) {
        InfStoreDTO store = infStoreFeignClient.queryByDisplayName(influencerStoreQueryBO);
        countHits(store, IpUtil.getIpAddr(request));
        return store;
    }

    public void countHits(InfStoreDTO infStore, String ip) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:00");
        String key = ip + ":" + infStore.getDisplayName();
        LinkedHashMap data = ( LinkedHashMap ) redisUtil.hgetall(key);
        if (CollectionUtil.isEmpty(data)) {
            redisUtil.hset(key, date, INIT_COUNT, ExpireTime.ONE_DAY.getTime());
            logger.info("hits add，displayName:{}", infStore.getDisplayName());
            infUserHitsFeignClient.save(InfUserHitsSaveBO.builder().ip(ip).influencerId(infStore.getInfluencerId()).createTime(LocalDateTime.now()).build());
        } else {
            Optional createTime = data.keySet().stream().findFirst();
            if (createTime.isPresent()) {
                String time = createTime.get().toString();
                int count = 0;
                if (CollectionUtil.isNotEmpty(data.values())) {
                    count = ( Integer ) data.values().stream().findFirst().get();
                }
                String fTime = com.love.common.util.DateUtil.offsetMinuteStr(time, "yyyy-MM-dd HH:mm:00", 30);
                int diff = com.love.common.util.DateUtil.compareDateTime(DateUtil.parseDateTime(date), DateUtil.parseDateTime(fTime));
                if (diff == 1) {
                    logger.info("hits不变，displayName:{}", infStore.getDisplayName());
                    data.keySet().forEach(o -> redisUtil.hdel(key, o));
                    redisUtil.hset(key, date, count + 1, ExpireTime.ONE_DAY.getTime());
                    infUserHitsFeignClient.save(InfUserHitsSaveBO.builder().ip(ip).influencerId(infStore.getInfluencerId()).createTime(LocalDateTime.now()).build());
                }
            }
        }
    }
}
