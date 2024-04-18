package com.love.marketplace.manager;


import cn.hutool.core.bean.BeanUtil;
import com.github.slugify.Slugify;
import com.love.common.bo.KeyQueryBO;
import com.love.common.client.KeyValueFeignClient;
import com.love.common.dto.KeyValueDTO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.util.BeanUtils;
import com.love.common.util.GsonUtil;
import com.love.common.util.ObjectUtil;
import com.love.common.util.PageableUtil;
import com.love.goods.bo.GoodsDetailQueryBO;
import com.love.goods.bo.GoodsHomepageQueryBO;
import com.love.goods.bo.GoodsRecommendQueryBO;
import com.love.goods.bo.GoodsStatusPageQueryBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.GoodsDTO;
import com.love.goods.dto.GoodsHomepageDTO;
import com.love.goods.dto.RecommendGoodsDTO;
import com.love.goods.enums.GoodsStatus;
import com.love.marketplace.model.param.GoodsHomepageQueryParam;
import com.love.marketplace.model.param.GoodsRecommendQueryParam;
import com.love.marketplace.model.vo.GoodsHomepageVO;
import com.love.marketplace.model.vo.GoodsSkuVO;
import com.love.marketplace.model.vo.GoodsVO;
import com.love.marketplace.model.vo.HomepageV2VO;
import com.love.mq.enums.GoodsOperationType;
import com.love.mq.message.GoodsUpdateMessage;
import com.love.mq.sender.impl.GoodsMessageSender;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsManager {
    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);

    private final GoodsFeignClient goodsFeignClient;
    private final KeyValueFeignClient keyValueFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final GoodsMessageSender goodsMessageSender;

    public Pageable<GoodsHomepageVO> trendingNow() {
        KeyValueDTO keyValue = keyValueFeignClient.queryByKey(KeyQueryBO.builder().key("trending.now").build());
        Pageable<GoodsHomepageVO> pageable = new Pageable<>(1, 10);
        pageable.setTotal(8);
        pageable.setRecords(BeanUtils.copyList(goodsFeignClient.queryForHomepageByIds(IdsParam.builder().idStr(keyValue.getValue()).build()), GoodsHomepageVO.class, (src, dst) -> dst.setSlug(slugify.slugify(src.getTitle()) + "_" + src.getId())));
        return pageable;
    }

    public Pageable<GoodsHomepageVO> page(GoodsHomepageQueryParam goodsHomepageQueryParam) {
        GoodsHomepageQueryBO goodsHomepageQueryBO = BeanUtil.copyProperties(goodsHomepageQueryParam, GoodsHomepageQueryBO.class);
        Pageable<GoodsHomepageDTO> pageable = goodsFeignClient.homepage(goodsHomepageQueryBO);
        return PageableUtil.toPage(pageable, GoodsHomepageVO.class, (src, dst) -> dst.setSlug(slugify.slugify(src.getTitle()) + "_" + src.getId()));
    }

    public List<HomepageV2VO> pageV2() {
        List<HomepageV2VO> result = new ArrayList<>();
        KeyValueDTO keyValue = keyValueFeignClient.queryByKey(KeyQueryBO.builder().key("homepage.v2").build());
        String value = ObjectUtil.ifNull(keyValue.getValue(), "{}");
        Map<String, String> config = GsonUtil.getMap(value, String.class, String.class);
        config.forEach((name, idStr) -> {
            List<GoodsHomepageDTO> list = goodsFeignClient.queryForHomepageByIds(IdsParam.builder().idStr(idStr).build());
            List<GoodsHomepageVO> destList = BeanUtils.copyList(list, GoodsHomepageVO.class, (src, dst) -> dst.setSlug(slugify.slugify(src.getTitle()) + "_" + src.getId()));
            int idx1 = name.indexOf(" ");
            int idx2 = name.lastIndexOf(" ");
            String slug = slugify.slugify(name.substring(idx1, idx2).trim().toLowerCase());
            result.add(HomepageV2VO.builder().name(name).data(destList).slug(slug).build());
        });
        return result;
    }

    public GoodsVO detail(IdParam idParam) {
        GoodsDTO goodsDTO = goodsFeignClient.detail(GoodsDetailQueryBO.builder().id(idParam.getId()).build());
        GoodsVO goodsVO = BeanUtil.copyProperties(goodsDTO, GoodsVO.class);
        goodsVO.getSkus().sort(Comparator.comparing(GoodsSkuVO::getPrice));
        goodsVO.setShippingModels(2);
        goodsVO.getVariants().forEach(x -> {
            String name = x.getName();
            if (name.contains(" ")) {
                x.setName(Stream.of(name.split("\\s")).map(StringUtils::capitalize).collect(Collectors.joining(" ")));
            } else {
                x.setName(StringUtils.capitalize(name));
            }
        });
        return goodsVO;
    }

    public List<RecommendGoodsDTO> queryRecommendGoodsByCategory(GoodsRecommendQueryParam goodsRecommendQueryParam) {
        GoodsRecommendQueryBO goodsRecommendQueryBO = BeanUtil.copyProperties(goodsRecommendQueryParam, GoodsRecommendQueryBO.class);
        return goodsFeignClient.queryRecommendGoodsByCategory(goodsRecommendQueryBO);
    }

    public Boolean updateIndex(String goodsId) {
        if ("all".equalsIgnoreCase(goodsId)) {
            int pageNum = 1;
            GoodsHomepageQueryBO goodsHomepageQueryBO = new GoodsHomepageQueryBO();
            goodsHomepageQueryBO.setPageNum(pageNum);
            goodsHomepageQueryBO.setPageSize(40);
            Pageable<GoodsHomepageDTO> pageable;
            do {
                pageable = goodsFeignClient.homepage(goodsHomepageQueryBO);
                pageable.getRecords().forEach(dto -> {
                    GoodsUpdateMessage goodsUpdateMessage = GoodsUpdateMessage.builder().goodsOperationType(GoodsOperationType.UPDATE).id(dto.getId()).build();
                    goodsMessageSender.sendGoodsUpdateMessage(goodsUpdateMessage);
                });
                goodsHomepageQueryBO.setPageNum(pageNum++);
            } while (!pageable.getRecords().isEmpty());
        } else {
            GoodsUpdateMessage goodsUpdateMessage = GoodsUpdateMessage.builder().goodsOperationType(GoodsOperationType.UPDATE).id(Long.parseLong(goodsId)).build();
            goodsMessageSender.sendGoodsUpdateMessage(goodsUpdateMessage);
        }
        return true;
    }

    public List<GoodsHomepageVO> popularPage() {
        GoodsStatusPageQueryBO goodsStatusPageQueryBO = GoodsStatusPageQueryBO.builder().status(GoodsStatus.ON_SALES.getStatus()).build();
        List<GoodsHomepageDTO> goodsHomepageDTOList = goodsFeignClient.popularPage(goodsStatusPageQueryBO);

        Map<Integer, Long> goodsInvetoryMap = goodsSkuFeignClient.queryAvailableStockByGoodsIds(IdsParam.builder().idList(goodsHomepageDTOList.stream().map(GoodsHomepageDTO::getId).collect(Collectors.toList())).build());
        goodsHomepageDTOList.forEach(g -> g.setAvailableStock(goodsInvetoryMap.get(g.getId().intValue())));

        return BeanUtils.copyList(goodsHomepageDTOList, GoodsHomepageVO.class, (src, dst) -> dst.setSlug(slugify.slugify(src.getTitle()) + "_" + src.getId()));
    }
}
