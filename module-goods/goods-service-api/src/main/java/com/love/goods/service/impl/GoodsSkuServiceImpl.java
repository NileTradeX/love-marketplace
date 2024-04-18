package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.enums.YesOrNo;
import com.love.common.exception.BizException;
import com.love.common.util.GsonUtil;
import com.love.goods.bo.*;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.goods.dto.GoodsSkuSimpleDTO;
import com.love.goods.entity.GoodsSku;
import com.love.goods.mapper.GoodsSkuMapper;
import com.love.goods.service.GoodsService;
import com.love.goods.service.GoodsSkuService;
import com.love.mq.message.GoodsUpdateMessage;
import com.love.mq.sender.impl.GoodsMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {

    @Autowired
    private GoodsService goodsService;

    private final GoodsMessageSender goodsMessageSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchSave(Long goodsId, List<GoodsSkuSaveBO> skuBOList) {
        // if need update
        List<GoodsSku> needUpdatedSkus = skuBOList.stream().filter(skuBO -> Objects.nonNull(skuBO.getId())).map(skuBO -> BeanUtil.copyProperties(skuBO, GoodsSku.class)).collect(Collectors.toList());
        List<GoodsSkuSaveBO> needAddSkus = skuBOList.stream().filter(skuBO -> Objects.isNull(skuBO.getId())).collect(Collectors.toList());

        // if seems to need save
        List<GoodsSku> databaseSkus = this.lambdaQuery().eq(GoodsSku::getGoodsId, goodsId).list();
        Map<String, GoodsSku> databaseSkuMap = databaseSkus.stream().collect(Collectors.toMap(sku -> toValueStr(sku.getAttrValueJson()), sku -> sku, (k1, k2) -> k1));
        List<GoodsSku> needSavedSkus = new ArrayList<>();
        needAddSkus.forEach(skuBO -> {
            String valueStr = toValueStr(skuBO.getAttrValueJson());
            GoodsSku sku = databaseSkuMap.get(valueStr);
            if (Objects.nonNull(sku)) {
                // need update
                BeanUtil.copyProperties(skuBO, sku, "id");
                //Revert previous deleted state
                sku.setDeleted(YesOrNo.NO.getVal());
                needUpdatedSkus.add(sku);
            } else {
                // need save
                GoodsSku needSavedSku = BeanUtil.copyProperties(skuBO, GoodsSku.class);
                needSavedSkus.add(needSavedSku);
            }
        });

        List<Long> skuIds = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(needUpdatedSkus)) {
            this.updateBatchById(needUpdatedSkus);
            skuIds.addAll(needUpdatedSkus.stream().map(GoodsSku::getId).collect(Collectors.toList()));
        }

        if (CollectionUtil.isNotEmpty(needSavedSkus)) {
            this.saveBatch(needSavedSkus);
            skuIds.addAll(needSavedSkus.stream().map(GoodsSku::getId).collect(Collectors.toList()));
        }

        // need delete(soft)
        List<Long> databaseSkuIds = databaseSkus.stream().map(GoodsSku::getId).collect(Collectors.toList());
        databaseSkuIds.removeAll(skuIds);
        if (CollectionUtil.isNotEmpty(databaseSkuIds)) {
            this.deleteByIds(databaseSkuIds.toArray(new Long[0]));
        }
        return skuIds;
    }

    private String toValueStr(String valueJson) {
        JSONArray array = JSONArray.parseArray(valueJson);
        List<String> values = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            String value = jsonObject.getString("value");
            values.add(value);
        }
        return values.stream().sorted().collect(Collectors.joining("/"));
    }

    @Override
    public boolean deleteByGoodsId(Long goodsId) {
        return this.lambdaUpdate().set(GoodsSku::getDeleted, 1).eq(GoodsSku::getGoodsId, goodsId).update();
    }

    @Override
    public boolean deleteByIds(Long[] ids) {
        return this.lambdaUpdate().set(GoodsSku::getDeleted, 1).in(GoodsSku::getId, ids).update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> duplicateSkus(Long oldGoodsId, Long newGoodsId) {
        List<GoodsSku> list = this.lambdaQuery().eq(GoodsSku::getGoodsId, oldGoodsId).eq(GoodsSku::getDeleted, 0).list();
        for (GoodsSku sku : list) {
            sku.setId(null);
            sku.setGoodsId(newGoodsId);
            sku.setCreateBy(null);
            sku.setUpdateBy(null);
            sku.setCreateTime(null);
            sku.setUpdateTime(null);
            sku.setAvailableStock(0);
            sku.setOnHandStock(0);
            sku.setCommittedStock(0);
            sku.setDeleted(0);
        }
        this.saveBatch(list);
        return list.stream().map(GoodsSku::getId).collect(Collectors.toList());
    }

    @Override
    public List<GoodsSkuDTO> queryByGoodsId(Long goodsId, boolean withDeleted) {
        List<GoodsSku> list = this.lambdaQuery().eq(GoodsSku::getGoodsId, goodsId).eq(!withDeleted, GoodsSku::getDeleted, 0).orderByAsc(GoodsSku::getId).list();
        return BeanUtil.copyToList(list, GoodsSkuDTO.class);
    }

    @Override
    public List<GoodsSkuDTO> queryByIds(List<Long> skuIdList) {
        List<GoodsSku> list = this.lambdaQuery().in(GoodsSku::getId, skuIdList).list();
        return BeanUtil.copyToList(list, GoodsSkuDTO.class);
    }

    @Override
    public GoodsSkuDTO queryById(Long id) {
        GoodsSkuDTO goodsSkuDTO = BeanUtil.copyProperties(this.getById(id), GoodsSkuDTO.class);
        String attrJson = goodsSkuDTO.getAttrValueJson();
        List<Map<String, Object>> list = GsonUtil.getListMap(attrJson, String.class, Object.class);
        StringBuilder builder = new StringBuilder();
        list.forEach(x -> x.forEach(((k, v) -> {
            if (k.equals("value")) {
                builder.append(v).append("/");
            }
        })));
        if (builder.length() > 0) {
            goodsSkuDTO.setAttrValues(builder.deleteCharAt(builder.length() - 1).toString());
        }
        return goodsSkuDTO;
    }

    @Override
    public GoodsSkuSimpleDTO simple(Long id) {
        GoodsSku goodsSku = this.lambdaQuery().select(GoodsSku::getId, GoodsSku::getMerchantId, GoodsSku::getPrice, GoodsSku::getOnHandStock, GoodsSku::getCommittedStock, GoodsSku::getAvailableStock).eq(GoodsSku::getId, id).one();
        return BeanUtil.copyProperties(goodsSku, GoodsSkuSimpleDTO.class);
    }

    @Override
    public Boolean modifyCommittedStock(ModifyGoodsSkuCommittedStockBO modifyGoodsSkuCommittedStockBO) {
        Integer commit = modifyGoodsSkuCommittedStockBO.getCommittedStock();
        Long skuId = modifyGoodsSkuCommittedStockBO.getSkuId();

        boolean result = false;
        if (commit > 0) {
            result = this.lambdaUpdate().setSql("available_stock = available_stock - " + commit + ", committed_stock = committed_stock + " + commit)
                    .set(GoodsSku::getUpdateTime, LocalDateTime.now())
                    .eq(GoodsSku::getId, skuId)
                    .ge(GoodsSku::getAvailableStock, commit)
                    .update();
        } else if (commit < 0) {
            int absCommit = Math.abs(commit);
            result = this.lambdaUpdate().setSql("available_stock = available_stock + " + absCommit + ", committed_stock = committed_stock - " + absCommit)
                    .set(GoodsSku::getUpdateTime, LocalDateTime.now())
                    .eq(GoodsSku::getId, skuId)
                    .ge(GoodsSku::getCommittedStock, absCommit)
                    .update();
        }

        if (result) {
            GoodsSkuDTO goodsSkuDTO = this.queryById(skuId);
            if (Objects.nonNull(goodsSkuDTO)) {
                goodsMessageSender.sendGoodsUpdateMessage(GoodsUpdateMessage.builder().id(goodsSkuDTO.getGoodsId()).build());
            }
        }
        return result;
    }

    private boolean modifyAvailableStock(Long skuId, int differStock) {
        if (differStock > 0) {
            return this.lambdaUpdate().setSql("available_stock = available_stock + " + differStock + ", on_hand_stock = on_hand_stock + " + differStock).eq(GoodsSku::getId, skuId).update();
        } else if (differStock < 0) {
            int absDifferStock = Math.abs(differStock);
            return this.lambdaUpdate().setSql("available_stock = available_stock - " + absDifferStock + ", on_hand_stock = on_hand_stock - " + absDifferStock).eq(GoodsSku::getId, skuId).ge(GoodsSku::getAvailableStock, absDifferStock).ge(GoodsSku::getOnHandStock, absDifferStock).update();
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyAvailableStock(ModifyGoodsSkuAvailableStockBO modifyGoodsSkuAvailableStockBO) {
        Map<Long, Integer> skuStockModifyMap = modifyGoodsSkuAvailableStockBO.getModifySkuAvailableStockList().stream().collect(Collectors.toMap(ModifySkuAvailableStockBO::getSkuId, ModifySkuAvailableStockBO::getAvailableStock));
        List<GoodsSkuDTO> goodsSkuDTOS = queryByIds(modifyGoodsSkuAvailableStockBO.getModifySkuAvailableStockList().stream().map(ModifySkuAvailableStockBO::getSkuId).collect(Collectors.toList()));
        Map<Long, GoodsSkuDTO> goodsSkuDTOMap = goodsSkuDTOS.stream().collect(Collectors.toMap(GoodsSkuDTO::getId, Function.identity()));

        if (skuStockModifyMap.size() != goodsSkuDTOMap.size()) {
            throw BizException.build("Sku data not found.");
        }

        boolean skuFailFlag = modifyGoodsSkuAvailableStockBO.getModifySkuAvailableStockList().stream().anyMatch(t -> !modifyAvailableStock(t.getSkuId(), t.getAvailableStock() - goodsSkuDTOMap.get(t.getSkuId()).getAvailableStock()));
        if (skuFailFlag) {
            throw BizException.build("Modify Sku Stock Error,Please Retry.");
        } else {
            boolean goodsStockFlag = goodsService.modifyOnHandStock(goodsSkuDTOS.get(0).getGoodsId(), goodsSkuDTOMap.values().stream().mapToInt(t -> skuStockModifyMap.get(t.getId()) - t.getAvailableStock()).sum());
            if (!goodsStockFlag) {
                throw BizException.build("Modify Goods Stock Error,Please Retry.");
            }
        }
        goodsMessageSender.sendGoodsUpdateMessage(GoodsUpdateMessage.builder().id(goodsSkuDTOS.get(0).getGoodsId()).build());
        return true;
    }

    @Override
    public Integer queryAvailableStockByGoodsId(Long goodsId) {
        return this.getObj(Wrappers.<GoodsSku>query().select("sum(available_stock)").eq("goods_id", goodsId).eq("deleted", 0), x -> new Integer(x.toString()));
    }

    public Boolean modifyDefaultSku(ModifyDefaultSkuBO modifyDefaultSkuBO) {
        if (modifyDefaultSkuBO.getSkuId() != null) {
            boolean result = this.lambdaUpdate().set(GoodsSku::getDefaultSku, YesOrNo.NO.getVal()).eq(GoodsSku::getGoodsId, modifyDefaultSkuBO.getGoodsId()).update();
            return result && this.lambdaUpdate().set(GoodsSku::getDefaultSku, YesOrNo.YES.getVal()).eq(GoodsSku::getId, modifyDefaultSkuBO.getSkuId()).update();
        }
        return false;
    }

    @Override
    public Map<Integer, Long> queryAvailableStockByGoodsIds(List<Long> goodsIds) {
        QueryWrapper<GoodsSku> queryWrapper = Wrappers.<GoodsSku>query()
                .select("goods_id, sum(available_stock) as total_stock")
                .in("goods_id", goodsIds)
                .eq("deleted", 0)
                .groupBy("goods_id");

        return this.listMaps(queryWrapper).stream().collect(Collectors.toMap(k -> ( Integer ) k.get("goods_id"), v -> ( Long ) v.get("total_stock")));
    }

    @Override
    public Map<Long, String> querySkuSpecs(List<Long> skuIds) {
        List<GoodsSku> goodsSkuList = this.lambdaQuery().select(GoodsSku::getId, GoodsSku::getAttrValueJson)
                .in(GoodsSku::getId, skuIds).list();
        Map<Long, String> skuMap = goodsSkuList.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getAttrValueJson()));
        for (Long skuId : skuMap.keySet()) {
            skuMap.put(skuId, toValueStr(skuMap.get(skuId)));
        }
        return skuMap;
    }
}
