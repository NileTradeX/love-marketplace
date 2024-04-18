package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.slugify.Slugify;
import com.love.common.exception.BizException;
import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.param.UserIdParam;
import com.love.common.util.ObjectUtil;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.BrandDTO;
import com.love.goods.dto.GoodsDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.goods.dto.GoodsSkuSimpleDTO;
import com.love.marketplace.model.param.ShoppingCartGoodsMergeParam;
import com.love.marketplace.model.param.ShoppingCartGoodsQueryListParam;
import com.love.marketplace.model.param.ShoppingCartGoodsSaveParam;
import com.love.marketplace.model.vo.ShoppingCartVO;
import com.love.user.client.ShoppingCartFeignClient;
import com.love.user.sdk.bo.ShoppingCartGoodsMergeBO;
import com.love.user.sdk.bo.ShoppingCartGoodsQueryListBO;
import com.love.user.sdk.bo.ShoppingCartGoodsSaveBO;
import com.love.user.sdk.dto.ShoppingCartGoodsDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShoppingCartManager {

    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);

    private final GoodsFeignClient goodsFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final ShoppingCartFeignClient shoppingCartFeignClient;

    public Boolean saveGoods(ShoppingCartGoodsSaveParam shoppingCartGoodsSaveParam) {
        // The update operation requires merge
        if (Objects.nonNull(shoppingCartGoodsSaveParam.getId())) {
            List<ShoppingCartGoodsDTO> shoppingCartGoodsDTOS = shoppingCartFeignClient.goodsList(ShoppingCartGoodsQueryListBO.builder()
                    .userId(shoppingCartGoodsSaveParam.getUserId())
                    .goodsId(shoppingCartGoodsSaveParam.getGoodsId())
                    .skuId(shoppingCartGoodsSaveParam.getSkuId())
                    .build());
            Long skuId = shoppingCartGoodsSaveParam.getSkuId();
            if (CollectionUtil.isNotEmpty(shoppingCartGoodsDTOS)) {
                Optional<ShoppingCartGoodsDTO> oldOptional = shoppingCartGoodsDTOS.stream()
                        .filter(e -> !shoppingCartGoodsSaveParam.getId().equals(e.getId()) && skuId.equals(e.getSkuId())).findFirst();
                // merge qty
                oldOptional.ifPresent(shoppingCartGoodsDTO -> shoppingCartGoodsSaveParam.setQty(shoppingCartGoodsDTO.getQty() + shoppingCartGoodsSaveParam.getQty()));
            }
        }
        GoodsSkuSimpleDTO goodsSku = goodsSkuFeignClient.simple(IdParam.builder().id(shoppingCartGoodsSaveParam.getSkuId()).build());
        if (Objects.nonNull(goodsSku) && goodsSku.getAvailableStock() >= shoppingCartGoodsSaveParam.getQty()) {
            ShoppingCartGoodsSaveBO cartGoodsSaveBO = BeanUtil.copyProperties(shoppingCartGoodsSaveParam, ShoppingCartGoodsSaveBO.class);
            return shoppingCartFeignClient.saveGoods(cartGoodsSaveBO);
        }
        throw BizException.build("sku info does not exist or out of stock");
    }

    public Boolean deleteGoods(ByUserIdParam idParam) {
        return shoppingCartFeignClient.deleteGoods(idParam);
    }

    public ShoppingCartVO goodsList(ShoppingCartGoodsQueryListParam shoppingCartGoodsQueryListParam) {
        List<Long> goodsIdList;
        Map<String, ShoppingCartGoodsDTO> selectSkuIdMap;

        if (StringUtils.isNotBlank(shoppingCartGoodsQueryListParam.getGoodsIdSKuIdStr())) {
            String goodsIdSKuIdStr = shoppingCartGoodsQueryListParam.getGoodsIdSKuIdStr();
            selectSkuIdMap = new HashMap<>();
            goodsIdList = Stream.of(goodsIdSKuIdStr.split(",")).filter(StringUtils::isNotBlank).map(str -> {
                String[] arr = str.trim().split("_");
                Long goodsId = Long.valueOf(arr[0]);
                if (arr.length == 3) {
                    selectSkuIdMap.put(arr[0] + "_" + arr[1], ShoppingCartGoodsDTO.builder().goodsId(goodsId).skuId(Long.valueOf(arr[1])).qty(0).influencerCode(arr[2]).build());
                } else {
                    selectSkuIdMap.put(str, ShoppingCartGoodsDTO.builder().goodsId(goodsId).skuId(Long.valueOf(arr[1])).qty(0).build());
                }
                return goodsId;
            }).collect(Collectors.toList());
        } else {
            ShoppingCartGoodsQueryListBO shoppingCartGoodsQueryListBO = ShoppingCartGoodsQueryListBO.builder().userId(shoppingCartGoodsQueryListParam.getUserId()).build();
            List<ShoppingCartGoodsDTO> shoppingCartGoodsList = shoppingCartFeignClient.goodsList(shoppingCartGoodsQueryListBO);
            if (CollectionUtils.isEmpty(shoppingCartGoodsList)) {
                return null;
            }

            goodsIdList = shoppingCartGoodsList.stream().map(ShoppingCartGoodsDTO::getGoodsId).distinct().collect(Collectors.toList());
            selectSkuIdMap = shoppingCartGoodsList.stream().collect(Collectors.toMap(dto -> dto.getGoodsId() + "_" + dto.getSkuId(), Function.identity()));
        }

        List<GoodsDTO> goodsList = goodsFeignClient.queryByIds(IdsParam.builder().idList(goodsIdList).build());
        Map<Long, BrandDTO> brandCache = new HashMap<>();
        Map<Long, List<GoodsDTO>> brandGoodsMap = goodsList.stream().collect(Collectors.groupingBy(goodsDTO -> {
            Long brandId = goodsDTO.getBrand().getId();
            if (!brandCache.containsKey(brandId)) {
                brandCache.put(brandId, goodsDTO.getBrand());
            }
            return brandId;
        }));

        List<ShoppingCartVO.Brand> brands = new ArrayList<>();
        brandGoodsMap.forEach((brandId, list) -> {
            BrandDTO brandDTO = brandCache.get(brandId);
            ShoppingCartVO.Brand brand = BeanUtil.copyProperties(brandDTO, ShoppingCartVO.Brand.class);
            brand.setSlug(slugify.slugify(brand.getName()));
            List<ShoppingCartVO.Item> items = new ArrayList<>();
            list.forEach(goods -> {
                for (GoodsSkuDTO sku : goods.getSkus()) {
                    String key = goods.getId() + "_" + sku.getId();
                    if (selectSkuIdMap.containsKey(key)) {
                        ShoppingCartGoodsDTO cartGoods = selectSkuIdMap.get(key);

                        ShoppingCartVO.Item item = new ShoppingCartVO.Item();
                        item.setId(cartGoods.getId());
                        item.setGoodsId(goods.getId());
                        item.setTitle(goods.getTitle());
                        item.setSlug(goods.getTitle());
                        item.setStatus(goods.getStatus());
                        item.setMerchantId(goods.getMerchantId());
                        item.setFirstCateId(goods.getFirstCateId());
                        item.setSecondCateId(goods.getSecondCateId());
                        item.setInfluencerCode(cartGoods.getInfluencerCode());

                        ShoppingCartVO.Item.Sku selectSku = new ShoppingCartVO.Item.Sku();
                        selectSku.setId(sku.getId());
                        selectSku.setCover(ObjectUtil.ifNull(sku.getCover(), goods.getWhiteBgImg()));
                        selectSku.setPrice(sku.getPrice());
                        selectSku.setStock(sku.getAvailableStock());
                        selectSku.setAttrValue(sku.getAttrValues());
                        selectSku.setQty(cartGoods.getQty());
                        selectSku.setStatus(sku.getStatus());
                        item.setSku(selectSku);

                        items.add(item);
                    }
                }
            });
            brand.setGoodsList(items);
            brands.add(brand);
        });

        return ShoppingCartVO.builder().brands(brands).build();
    }

    public Boolean clear(UserIdParam userIdParam) {
        return shoppingCartFeignClient.clearByUserId(IdParam.builder().id(userIdParam.getUserId()).build());
    }

    public Boolean mergeGoods(ShoppingCartGoodsMergeParam shoppingCartGoodsMergeParam) {

        if (CollectionUtil.isEmpty(shoppingCartGoodsMergeParam.getItemList())) {
            return Boolean.TRUE;
        }
        List<Long> skuIdList = shoppingCartGoodsMergeParam.getItemList().stream().map(ShoppingCartGoodsMergeParam.Item::getSkuId).collect(Collectors.toList());
        List<GoodsSkuDTO> goodsSkuDTOS = goodsSkuFeignClient.queryByIds(IdsParam.builder().idList(skuIdList).build());
        if (CollectionUtil.isEmpty(goodsSkuDTOS)) {
            return Boolean.TRUE;
        }
        Map<Long, GoodsSkuDTO> skuMap = goodsSkuDTOS.stream().collect(Collectors.toMap(GoodsSkuDTO::getId, e -> e, (o1, o2) -> o1));
        ShoppingCartGoodsMergeBO shoppingCartGoodsMergeBO = BeanUtil.copyProperties(shoppingCartGoodsMergeParam, ShoppingCartGoodsMergeBO.class);
        // fill price
        List<ShoppingCartGoodsMergeBO.Item> itemList = shoppingCartGoodsMergeBO.getItemList();
        if (CollectionUtil.isNotEmpty(itemList)) {
            List<ShoppingCartGoodsMergeBO.Item> newList = new ArrayList<>();
            for (ShoppingCartGoodsMergeBO.Item item : itemList) {
                GoodsSkuDTO goodsSkuDTO = skuMap.get(item.getSkuId());
                if (Objects.nonNull(goodsSkuDTO)) {
                    item.setPrice(goodsSkuDTO.getPrice());
                    newList.add(item);
                }
            }
            shoppingCartGoodsMergeBO.setItemList(newList);
        }
        return shoppingCartFeignClient.mergeGoods(shoppingCartGoodsMergeBO);
    }
}
