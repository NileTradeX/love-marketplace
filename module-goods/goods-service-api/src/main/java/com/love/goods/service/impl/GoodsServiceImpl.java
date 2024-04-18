package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.slugify.Slugify;
import com.love.common.enums.YesOrNo;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.util.FastjsonUtil;
import com.love.common.util.GsonUtil;
import com.love.common.util.ObjectUtil;
import com.love.common.util.PageUtil;
import com.love.goods.bo.*;
import com.love.goods.dto.*;
import com.love.goods.entity.Goods;
import com.love.goods.entity.GoodsDoc;
import com.love.goods.enums.GoodsReviewStatus;
import com.love.goods.enums.GoodsStatus;
import com.love.goods.enums.GoodsType;
import com.love.goods.enums.LabelType;
import com.love.goods.mapper.GoodsMapper;
import com.love.goods.service.*;
import com.love.mq.enums.GoodsOperationType;
import com.love.mq.message.GoodsUpdateMessage;
import com.love.mq.sender.impl.GoodsMessageSender;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    public static final int RECOMMEND_GOODS_SIZE = 4;
    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);

    @Autowired
    private BrandService brandService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private CategoryService categoryService;

    private final LabelService labelService;
    private final AttrNameService attrNameService;
    private final GoodsDocService goodsDocService;
    private final GoodsMessageSender goodsMessageSender;

    private void buildVariants(Goods goods, GoodsDTO goodsDTO, boolean withDeleted) {
        List<GoodsSkuDTO> skuDTOList = goodsSkuService.queryByGoodsId(goods.getId(), withDeleted);
        Map<Long, AttrNameDTO> attrNameMap = new LinkedHashMap<>();
        for (GoodsSkuDTO skuDTO : skuDTOList) {
            String attrValueJson = skuDTO.getAttrValueJson();
            JSONArray attrArray = JSON.parseArray(attrValueJson);
            List<String> values = new ArrayList<>();
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attrJson = attrArray.getJSONObject(i);
                Long attrId = attrJson.getLong("attrId");
                AttrNameDTO nameDTO = attrNameMap.getOrDefault(attrId, AttrNameDTO.builder().id(attrId).name(attrJson.getString("name")).values(new LinkedHashSet<>()).build());
                Long valueId = attrJson.getLong("valueId");
                String value = attrJson.getString("value");
                Integer sortNum = Objects.isNull(attrJson.getInteger("sortNum")) ? 0 : attrJson.getInteger("sortNum");
                values.add(value);
                nameDTO.getValues().add(AttrValueDTO.builder().attrNameId(attrId).id(valueId).value(value).sortNum(sortNum).build());
                attrNameMap.put(attrId, nameDTO);
            }
            skuDTO.setAttrValues(String.join("/", values));
        }
        goodsDTO.setVariants(new ArrayList<>(attrNameMap.values()));
        goodsDTO.setSkus(skuDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long savePhysical(PhysicalGoodsSaveBO physicalGoodsSaveBO) {
        if (GoodsType.PHYSICAL.getType() != physicalGoodsSaveBO.getType()) {
            throw BizException.build("Unsupported Goods type");
        }

        Goods goods = BeanUtil.copyProperties(physicalGoodsSaveBO, Goods.class);
        goods.setDescWarnings(ObjectUtil.ifNull(goods.getDescWarnings(), ""));
        goods.setDescText(ObjectUtil.ifNull(goods.getDescText(), ""));
        goods.setIntro(ObjectUtil.ifNull(goods.getIntro(), ""));
        if (Objects.isNull(physicalGoodsSaveBO.getId())) {
            goods.setSalesVolume(0);
        }

        if (physicalGoodsSaveBO.isPublish()) {
            goods.setStatus(GoodsStatus.UNDER_REVIEW.getStatus());
            goods.setReviewStatus(GoodsReviewStatus.PENDING.getStatus());
            goods.setSubmissionTime(LocalDateTime.now());
        } else {
            goods.setStatus(GoodsStatus.DRAFT.getStatus());
            goods.setReviewStatus(GoodsReviewStatus.NONE.getStatus());
        }

        handleIngredient(physicalGoodsSaveBO, goods);

        List<GoodsSkuSaveBO> skuList = physicalGoodsSaveBO.getSkus();
        skuList.stream().filter(t -> t.getStatus() == 1).map(GoodsSkuSaveBO::getPrice).min(BigDecimal::compareTo).ifPresent(goods::setMinPrice);
        skuList.stream().filter(t -> t.getStatus() == 1).map(GoodsSkuSaveBO::getPrice).max(BigDecimal::compareTo).ifPresent(goods::setMaxPrice);
        int count = skuList.stream().filter(t -> t.getStatus() == 1).mapToInt(GoodsSkuSaveBO::getOnHandStock).sum();
        goods.setOnHandStock(count);

        this.saveOrUpdate(goods);

        if (CollUtil.isEmpty(physicalGoodsSaveBO.getKeyIngredients()) || CollUtil.isEmpty(physicalGoodsSaveBO.getIngredients())) {
            LambdaUpdateWrapper<Goods> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Goods::getId, goods.getId());
            if (physicalGoodsSaveBO.getIngredients() != null && CollUtil.isEmpty(physicalGoodsSaveBO.getIngredients())) {
                updateWrapper.set(Goods::getIngredientsJson, null);
            }
            if (physicalGoodsSaveBO.getKeyIngredients() != null && CollUtil.isEmpty(physicalGoodsSaveBO.getKeyIngredients())) {
                updateWrapper.set(Goods::getKeyIngredients, null);
            }
            updateWrapper.set(Goods::getUpdateTime, LocalDateTime.now());
            this.update(updateWrapper);
        }

        if (Objects.nonNull(physicalGoodsSaveBO.getGoodsDoc())) {
            GoodsDoc goodsDoc = BeanUtil.copyProperties(physicalGoodsSaveBO.getGoodsDoc(), GoodsDoc.class);
            goodsDoc.setGoodsId(goods.getId());
            goodsDocService.saveOrUpdate(goodsDoc);
        }

        Map<String, Map<String, JSONObject>> variantsValueMap = attrNameService.batchSave(physicalGoodsSaveBO.getVariants());
        List<String> attrNameList = physicalGoodsSaveBO.getVariants().stream().map(AttrNameSaveBO::getName).collect(Collectors.toList());
        Long defaultSkuId = null;
        for (GoodsSkuSaveBO sku : skuList) {
            if (Objects.nonNull(sku.getDefaultSku()) && YesOrNo.YES.getVal() == sku.getDefaultSku()) {
                defaultSkuId = sku.getId();
            }
            JSONArray array = new JSONArray();
            String[] arr = sku.getAttrValues().split("/");
            for (int i = 0; i < arr.length; i++) {
                String attrName = attrNameList.get(i);
                array.add(variantsValueMap.get(attrName).get(arr[i]));
            }
            sku.setAttrValueJson(array.toJSONString());
            sku.setGoodsId(goods.getId());
            sku.setMerchantId(goods.getMerchantId());
        }
        goodsSkuService.batchSave(goods.getId(), skuList);
        if (Objects.nonNull(physicalGoodsSaveBO.getId()) && Objects.nonNull(defaultSkuId)) {
            goodsSkuService.modifyDefaultSku(ModifyDefaultSkuBO.builder().skuId(defaultSkuId).goodsId(physicalGoodsSaveBO.getId()).build());
        }
        return goods.getId();
    }

    @Override
    public GoodsDTO detail(GoodsDetailQueryBO goodsDetailQueryBO) {
        LambdaQueryChainWrapper<Goods> queryWrapper = this.lambdaQuery().eq(Goods::getId, goodsDetailQueryBO.getId());

        List<Integer> statusList = goodsDetailQueryBO.getStatusList();
        if (CollUtil.isNotEmpty(statusList)) {
            if (statusList.size() == 1) {
                queryWrapper.eq(Goods::getStatus, statusList.get(0));
            } else {
                queryWrapper.in(Goods::getStatus, statusList);
            }
        }

        Goods goods = queryWrapper.one();
        if (Objects.isNull(goods)) {
            throw BizException.build("Product not found or not on-sale !");
        }

        GoodsDTO goodsDTO = BeanUtil.copyProperties(goods, GoodsDTO.class, "keyIngredients");
        if (Objects.nonNull(goods.getFirstCateId())) {
            goodsDTO.setFirstCategory(BeanUtil.copyProperties(categoryService.queryById(IdParam.builder().id(goods.getFirstCateId()).build()), CategoryDTO.class));
        }

        if (Objects.nonNull(goods.getSecondCateId())) {
            goodsDTO.setSecondCategory(BeanUtil.copyProperties(categoryService.queryById(IdParam.builder().id(goods.getSecondCateId()).build()), CategoryDTO.class));
        }

        goodsDTO.setBrand(brandService.queryById(IdParam.builder().id(goods.getBrandId()).build()));

        if (StringUtils.isNotBlank(goods.getKeyIngredients())) {
            goodsDTO.setKeyIngredients(labelService.queryByIds(goods.getKeyIngredients(), LabelType.INGREDIENT));
        }

        String ingredientsJson = goods.getIngredientsJson();
        if (StringUtils.isNotBlank(ingredientsJson)) {
            Map<String, IngredientDTO> ingredientDTOMap = FastjsonUtil.getList(ingredientsJson, IngredientDTO.class).stream().collect(Collectors.toMap(IngredientDTO::getName, x -> x, (k1, k2) -> k1));
            Optional.ofNullable(ingredientDTOMap.remove("servingSize")).ifPresent(x -> goodsDTO.setServingSize(x.getAmount()));
            Optional.ofNullable(ingredientDTOMap.remove("servingPerContainer")).ifPresent(x -> goodsDTO.setServingPerContainer(x.getAmount()));
            goodsDTO.setIngredients(new ArrayList<>(ingredientDTOMap.values()));
        }

        buildVariants(goods, goodsDTO, false);

        if (Objects.nonNull(goodsDetailQueryBO.getSkuStatus())) {
            goodsDTO.setSkus(goodsDTO.getSkus().stream().filter(goodsSkuDTO -> goodsSkuDTO.getStatus().equals(goodsDetailQueryBO.getSkuStatus())).collect(Collectors.toList()));
        }

        GoodsDocDTO goodsDocDTO = goodsDocService.queryByGoodsId(goods.getId());
        goodsDTO.setGoodsDoc(goodsDocDTO);

        goodsDTO.setSlug(slugify.slugify(goodsDTO.getTitle() + "_" + goodsDTO.getId()));
        return goodsDTO;
    }

    @Override
    public GoodsSimpleDTO simple(IdParam idParam) {
        Goods goods = this.lambdaQuery().select(Goods::getId, Goods::getTitle, Goods::getAffiliateLink, Goods::getWhiteBgImg, Goods::getMinPrice, Goods::getMaxPrice, Goods::getOnHandStock, Goods::getStatus, Goods::getMerchantId, Goods::getSalesVolume, Goods::getCreateTime, Goods::getSubTitle).eq(Goods::getId, idParam.getId()).one();
        if (Objects.isNull(goods)) {
            throw BizException.build("Product not found!");
        }
        return BeanUtil.copyProperties(goods, GoodsSimpleDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(IdParam idParam) {
        boolean result = this.lambdaUpdate().set(Goods::getDeleted, 1).eq(Goods::getId, idParam.getId()).update();
        if (result) {
            return goodsSkuService.deleteByGoodsId(idParam.getId());
        }
        return false;
    }

    @Override
    public Pageable<GoodsSimpleDTO> page(GoodsQueryPageBO goodsQueryPageBO) {
        LambdaQueryChainWrapper<Goods> queryWrapper = this.lambdaQuery().eq(Objects.nonNull(goodsQueryPageBO.getMerchantId()), Goods::getMerchantId, goodsQueryPageBO.getMerchantId())
                .eq(Goods::getDeleted, 0)
                .eq(Objects.nonNull(goodsQueryPageBO.getType()), Goods::getType, goodsQueryPageBO.getType())
                .in(Objects.nonNull(goodsQueryPageBO.getFirstCateId()), Goods::getFirstCateId, goodsQueryPageBO.getFirstCateId())
                .in(Objects.nonNull(goodsQueryPageBO.getSecondCateId()), Goods::getSecondCateId, goodsQueryPageBO.getSecondCateId())
                .eq(Objects.nonNull(goodsQueryPageBO.getStatus()), Goods::getStatus, goodsQueryPageBO.getStatus())
                .like(StringUtils.isNotBlank(goodsQueryPageBO.getTitle()), Goods::getTitle, goodsQueryPageBO.getTitle())
                .eq(Objects.nonNull(goodsQueryPageBO.getId()), Goods::getId, goodsQueryPageBO.getId())
                .ge(Objects.nonNull(goodsQueryPageBO.getStartCreationDate()), Goods::getCreateTime, goodsQueryPageBO.getStartCreationDate())
                .le(Objects.nonNull(goodsQueryPageBO.getEndCreationDate()), Goods::getCreateTime, goodsQueryPageBO.getEndCreationDate())
                .ge(Objects.nonNull(goodsQueryPageBO.getMinSalesVolume()), Goods::getSalesVolume, goodsQueryPageBO.getMinSalesVolume())
                .le(Objects.nonNull(goodsQueryPageBO.getMaxSalesVolume()), Goods::getSalesVolume, goodsQueryPageBO.getMaxSalesVolume())
                .orderByDesc(Goods::getCreateTime);

        if (Objects.nonNull(goodsQueryPageBO.getMaxPrice())) {
            queryWrapper.le(Goods::getMinPrice, goodsQueryPageBO.getMaxPrice());
        }

        if (Objects.nonNull(goodsQueryPageBO.getMinPrice())) {
            queryWrapper.ge(Goods::getMaxPrice, goodsQueryPageBO.getMinPrice());
        }

        Page<Goods> page = queryWrapper.page(new Page<>(goodsQueryPageBO.getPageNum(), goodsQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, GoodsSimpleDTO.class, (src, dst) -> {
            if (Objects.nonNull(src.getFirstCateId())) {
                dst.setFirstCategory(BeanUtil.copyProperties(categoryService.queryById(IdParam.builder().id(src.getFirstCateId()).build()), CategoryDTO.class));
            }

            if (Objects.nonNull(src.getSecondCateId())) {
                dst.setSecondCategory(BeanUtil.copyProperties(categoryService.queryById(IdParam.builder().id(src.getSecondCateId()).build()), CategoryDTO.class));
            }
        });
    }

    @Override
    public Pageable<GoodsHomepageDTO> homepage(GoodsHomepageQueryBO goodsHomepageQueryBO) {
        PublishCategoryIdDTO publishCategoryIdDTO = categoryService.queryPublishCategoriesById(PublishCategoryQueryBO.builder().firstCateIds(goodsHomepageQueryBO.getFirstCateIds()).secondCateIds(goodsHomepageQueryBO.getSecondCateIds()).build());
        LambdaQueryChainWrapper<Goods> wrapper = this.lambdaQuery()
                .eq(Goods::getDeleted, 0)
                .in(CollUtil.isNotEmpty(publishCategoryIdDTO.getSecondCateIds()), Goods::getSecondCateId, publishCategoryIdDTO.getSecondCateIds())
                .in(CollUtil.isNotEmpty(goodsHomepageQueryBO.getBrandIds()), Goods::getBrandId, goodsHomepageQueryBO.getBrandIds());

        if (CollUtil.isNotEmpty(goodsHomepageQueryBO.getPriceRanges())) {
            wrapper.and(w -> goodsHomepageQueryBO.getPriceRanges().forEach(range -> w.or(wi -> {
                wi.ge(Goods::getMaxPrice, range.getMin());
                wi.le(Goods::getMinPrice, range.getMax());
            })));
        }

        Page<Goods> page = wrapper.eq(Goods::getStatus, GoodsStatus.ON_SALES.getStatus()).orderByDesc(Goods::getReviewTime).page(new Page<>(goodsHomepageQueryBO.getPageNum(), goodsHomepageQueryBO.getPageSize()));
        return PageUtil.toPage(page, GoodsHomepageDTO.class);
    }

    @Override
    public Pageable<GoodsReviewDTO> reviewPage(GoodsReviewPageQueryBO goodsReviewPageQueryBO) {
        Page<Goods> page = this.lambdaQuery().in(Objects.isNull(goodsReviewPageQueryBO.getReviewStatus()), Goods::getReviewStatus, GoodsReviewStatus.PENDING.getStatus(), GoodsReviewStatus.APPROVED.getStatus(), GoodsReviewStatus.REJECTED.getStatus())
                .eq(Goods::getDeleted, 0)
                .eq(Objects.nonNull(goodsReviewPageQueryBO.getReviewStatus()), Goods::getReviewStatus, goodsReviewPageQueryBO.getReviewStatus())
                .orderByDesc(Goods::getSubmissionTime).page(new Page<>(goodsReviewPageQueryBO.getPageNum(), goodsReviewPageQueryBO.getPageSize()));
        return PageUtil.toPage(page, GoodsReviewDTO.class);
    }

    @Override
    public List<GoodsReviewStatDTO> reviewStat() {
        Long numPending = this.lambdaQuery().eq(Goods::getReviewStatus, GoodsReviewStatus.PENDING.getStatus()).eq(Goods::getDeleted, 0).count();
        Long numRejected = this.lambdaQuery().eq(Goods::getReviewStatus, GoodsReviewStatus.REJECTED.getStatus()).eq(Goods::getDeleted, 0).count();
        List<GoodsReviewStatDTO> goodsReviewStatusDTOList = new ArrayList<>(4);
        goodsReviewStatusDTOList.add(GoodsReviewStatDTO.builder().name("All").build());
        goodsReviewStatusDTOList.add(GoodsReviewStatDTO.builder().status(GoodsReviewStatus.PENDING.getStatus()).name("Pending").number(numPending).build());
        goodsReviewStatusDTOList.add(GoodsReviewStatDTO.builder().status(GoodsReviewStatus.APPROVED.getStatus()).name("Approved").build());
        goodsReviewStatusDTOList.add(GoodsReviewStatDTO.builder().status(GoodsReviewStatus.REJECTED.getStatus()).name("Rejected").number(numRejected).build());
        return goodsReviewStatusDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewApprove(GoodsReviewApproveBO goodsReviewApproveBO) {
        boolean result = this.lambdaUpdate()
                .set(Goods::getReviewStatus, GoodsReviewStatus.APPROVED.getStatus())
                .set(Goods::getReviewTime, LocalDateTime.now())
                .set(Goods::getStatus, GoodsStatus.ON_SALES.getStatus())
                .set(Goods::getLoveScore, goodsReviewApproveBO.getLoveScore())
                .set(Goods::getWhyLove, goodsReviewApproveBO.getWhyLove())
                .eq(Goods::getId, goodsReviewApproveBO.getId())
                .eq(Goods::getReviewStatus, GoodsReviewStatus.PENDING.getStatus())
                .update();

        if (result) {
            goodsMessageSender.sendGoodsUpdateMessage(new GoodsUpdateMessage(goodsReviewApproveBO.getId(), GoodsOperationType.UPDATE));
        }
        return result;
    }

    @Override
    public boolean reviewReject(GoodsReviewRejectBO goodsReviewRejectBO) {
        return this.lambdaUpdate()
                .set(Goods::getReviewStatus, GoodsReviewStatus.REJECTED.getStatus())
                .set(Goods::getReviewTime, LocalDateTime.now())
                .set(Goods::getStatus, GoodsStatus.REVIEW_REJECTED.getStatus())
                .set(Goods::getReviewComment, goodsReviewRejectBO.getComment())
                .in(Goods::getId, goodsReviewRejectBO.getIds())
                .eq(Goods::getReviewStatus, GoodsReviewStatus.PENDING.getStatus())
                .update();
    }

    @Override
    public boolean putOff(IdParam idParam) {
        boolean result = this.lambdaUpdate().set(Goods::getStatus, GoodsStatus.DELISTED.getStatus())
                .set(Goods::getReviewStatus, GoodsReviewStatus.NONE.getStatus())
                .set(Goods::getUpdateTime, LocalDateTime.now())
                .eq(Goods::getId, idParam.getId())
                .eq(Goods::getStatus, GoodsStatus.ON_SALES.getStatus())
                .update();

        if (result) {
            goodsMessageSender.sendGoodsUpdateMessage(new GoodsUpdateMessage(idParam.getId(), GoodsOperationType.DELETE));
        }
        return result;
    }

    @Override
    public boolean putOn(IdParam idParam) {
        return this.lambdaUpdate().set(Goods::getStatus, GoodsStatus.UNDER_REVIEW.getStatus())
                .set(Goods::getReviewStatus, GoodsReviewStatus.PENDING.getStatus())
                .set(Goods::getSubmissionTime, LocalDateTime.now())
                .set(Goods::getUpdateTime, LocalDateTime.now())
                .eq(Goods::getId, idParam.getId())
                .in(Goods::getStatus, GoodsStatus.DELISTED.getStatus(), GoodsStatus.DRAFT.getStatus(), GoodsStatus.REVIEW_REJECTED.getStatus())
                .update();
    }

    @Override
    public Long duplicate(GoodsDuplicateBO goodsDuplicateBO) {
        Goods goods = this.getById(goodsDuplicateBO.getId());
        if (Objects.isNull(goods)) {
            throw BizException.build(String.format("No product found with id:%s", goodsDuplicateBO.getId()));
        }

        goods.setId(null);
        goods.setReviewComment(null);
        goods.setReviewTime(null);
        goods.setReviewStatus(GoodsReviewStatus.NONE.getStatus());
        goods.setStatus(GoodsStatus.DRAFT.getStatus());
        goods.setSubmissionTime(null);
        goods.setCreateBy(null);
        goods.setUpdateBy(null);
        goods.setCreateTime(null);
        goods.setUpdateTime(null);
        goods.setTitle(goodsDuplicateBO.getName());
        goods.setDeleted(0);
        goods.setOnHandStock(0);
        goods.setSalesVolume(0);
        this.save(goods);

        goodsSkuService.duplicateSkus(goodsDuplicateBO.getId(), goods.getId());

        GoodsDoc goodsDoc = new GoodsDoc();
        goodsDoc.setGoodsId(goods.getId());
        goodsDocService.saveOrUpdate(goodsDoc);

        return goods.getId();
    }

    private void handleIngredient(PhysicalGoodsSaveBO physicalGoodsSaveBO, Goods goods) {
        if (CollUtil.isNotEmpty(physicalGoodsSaveBO.getKeyIngredients())) {
            List<Long> keyIngredientLabelIds = labelService.batchSave(physicalGoodsSaveBO.getKeyIngredients(), LabelType.INGREDIENT);
            goods.setKeyIngredients(keyIngredientLabelIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        } else {
            goods.setKeyIngredients(null);
        }

        List<IngredientBO> ingredients = physicalGoodsSaveBO.getIngredients();
        if (CollUtil.isEmpty(ingredients)) {
            ingredients = new ArrayList<>();
        }

        String servingSize = physicalGoodsSaveBO.getServingSize();
        if (StringUtils.isNotBlank(servingSize)) {
            ingredients.add(IngredientBO.builder().name("servingSize").amount(servingSize).build());
        }

        String servingPerContainer = physicalGoodsSaveBO.getServingPerContainer();
        if (StringUtils.isNotBlank(servingPerContainer)) {
            ingredients.add(IngredientBO.builder().name("servingPerContainer").amount(servingPerContainer).build());
        }

        if (CollUtil.isNotEmpty(ingredients)) {
            goods.setIngredientsJson(GsonUtil.bean2json(ingredients));
        } else {
            goods.setIngredientsJson(null);
        }
    }

    @Override
    public Long countByBrandId(Long brandId) {
        return this.lambdaQuery().eq(Goods::getBrandId, brandId).eq(Goods::getDeleted, 0).eq(Goods::getDeleted, 0).count();
    }

    @Override
    public boolean modifyOnHandStock(Long goodsId, int differStock) {
        if (differStock > 0) {
            return this.lambdaUpdate().setSql("on_hand_stock = on_hand_stock + " + differStock).eq(Goods::getId, goodsId).update();
        } else if (differStock < 0) {
            int absDifferStock = Math.abs(differStock);
            return this.lambdaUpdate().setSql("on_hand_stock = on_hand_stock - " + absDifferStock).eq(Goods::getId, goodsId).ge(Goods::getOnHandStock, absDifferStock).update();
        }
        return true;
    }

    @Override
    public boolean modifySalesVolume(UpdateGoodsSalesVolumeBO goodsSalesVolumeUpdateBO) {
        return this.lambdaUpdate().setSql("sales_volume = sales_volume + " + goodsSalesVolumeUpdateBO.getQty()).eq(Goods::getId, goodsSalesVolumeUpdateBO.getId()).update();
    }

    @Override
    public long countByCategoryId(Long cateId, Integer cateLevel) {
        if (cateLevel == 1) {
            return this.lambdaQuery().eq(Goods::getFirstCateId, cateId).eq(Goods::getDeleted, 0).count();
        } else if (cateLevel == 2) {
            return this.lambdaQuery().eq(Goods::getSecondCateId, cateId).eq(Goods::getDeleted, 0).count();
        }
        return 0;
    }

    @Override
    public Pageable<InfGoodsPageDTO> influencerGoodsPage(InfGoodsPageQueryBO influencerGoodsPageQueryBO) {
        PublishCategoryIdDTO publishCategoryIdDTO = categoryService.queryPublishCategoriesById(PublishCategoryQueryBO.builder().firstCateIds(influencerGoodsPageQueryBO.getFirstCateIds()).secondCateIds(influencerGoodsPageQueryBO.getSecondCateIds()).build());
        LambdaQueryChainWrapper<Goods> wrapper = this.lambdaQuery()
                .select(Goods::getId, Goods::getTitle, Goods::getSubTitle, Goods::getWhiteBgImg, Goods::getMaxPrice, Goods::getMinPrice, Goods::getCommunityScore, Goods::getStatus)
                .in(CollUtil.isNotEmpty(publishCategoryIdDTO.getSecondCateIds()), Goods::getSecondCateId, publishCategoryIdDTO.getSecondCateIds())
                .in(CollUtil.isNotEmpty(influencerGoodsPageQueryBO.getBrandIds()), Goods::getBrandId, influencerGoodsPageQueryBO.getBrandIds())
                .notIn(CollUtil.isNotEmpty(influencerGoodsPageQueryBO.getGoodsId()), Goods::getId, influencerGoodsPageQueryBO.getGoodsId())
                .apply("id in( select a.goods_id from (select mgs.goods_id ,sum(mgs.available_stock) available_stock from love.m_goods_sku mgs where mgs.deleted = 0 and m_goods.id = mgs.goods_id and available_stock >0 group by mgs.goods_id) a)");

        Page<Goods> page = wrapper.eq(Goods::getDeleted, 0).eq(Goods::getStatus, GoodsStatus.ON_SALES.getStatus()).orderByDesc(Goods::getReviewTime).page(new Page<>(influencerGoodsPageQueryBO.getPageNum(), influencerGoodsPageQueryBO.getPageSize()));
        return PageUtil.toPage(page, InfGoodsPageDTO.class);
    }

    @Override
    public InfGoodsSimpleDTO simpleInfluenceGoods(IdParam idParam) {
        Goods goods = this.lambdaQuery().select(Goods::getTitle, Goods::getWhiteBgImg, Goods::getStatus, Goods::getSubTitle).eq(Goods::getId, idParam.getId()).one();
        if (Objects.isNull(goods)) {
            throw BizException.build("Product not found!");
        }
        return BeanUtil.copyProperties(goods, InfGoodsSimpleDTO.class);
    }

    @Override
    public List<GoodsHomepageDTO> queryForHomepageByIds(IdsParam idsParam) {
        List<Goods> goodsList = this.lambdaQuery().select(Goods::getId, Goods::getMinPrice, Goods::getMaxPrice, Goods::getWhiteBgImg, Goods::getSubTitle, Goods::getTitle, Goods::getLoveScore, Goods::getCommunityScore).in(Goods::getId, idsParam.getIdList()).list();
        return BeanUtil.copyToList(goodsList, GoodsHomepageDTO.class);
    }

    @Override
    public List<GoodsDTO> queryByIds(IdsParam idsParam) {
        List<Goods> goodsList = this.lambdaQuery().select(Goods::getId, Goods::getTitle, Goods::getStatus, Goods::getMinPrice, Goods::getMaxPrice, Goods::getMerchantId, Goods::getWhiteBgImg, Goods::getBrandId, Goods::getFirstCateId, Goods::getSecondCateId)
                .in(Goods::getId, idsParam.getIdList())
                .list();

        List<GoodsDTO> goodsDTOList = new ArrayList<>();
        for (Goods goods : goodsList) {
            GoodsDTO goodsDTO = BeanUtil.copyProperties(goods, GoodsDTO.class);
            buildVariants(goods, goodsDTO, true);
            goodsDTO.setBrand(brandService.queryById(IdParam.builder().id(goods.getBrandId()).build()));
            goodsDTOList.add(goodsDTO);
        }
        return goodsDTOList;
    }


    @Override
    public List<RecommendGoodsDTO> queryRecommendGoodsByCategory(GoodsRecommendQueryBO goodsRecommendQueryBO) {
        Integer recommendGoodsSize = goodsRecommendQueryBO.getRecommendGoodsSize();
        recommendGoodsSize = Objects.isNull(recommendGoodsSize) ? RECOMMEND_GOODS_SIZE : recommendGoodsSize;
        List<Goods> goodsList = this.lambdaQuery()
                .select(Goods::getId, Goods::getMinPrice, Goods::getMaxPrice, Goods::getWhiteBgImg, Goods::getSubTitle, Goods::getTitle, Goods::getLoveScore, Goods::getCommunityScore)
                .in(CollUtil.isNotEmpty(goodsRecommendQueryBO.getFirstCateIds()), Goods::getFirstCateId, goodsRecommendQueryBO.getFirstCateIds())
                .in(CollUtil.isNotEmpty(goodsRecommendQueryBO.getSecondCateIds()), Goods::getSecondCateId, goodsRecommendQueryBO.getSecondCateIds())
                .notIn(CollUtil.isNotEmpty(goodsRecommendQueryBO.getCurrentGoodsIds()), Goods::getId, goodsRecommendQueryBO.getCurrentGoodsIds())
                .eq(Goods::getDeleted, 0)
                .eq(Goods::getStatus, GoodsStatus.ON_SALES.getStatus())
                .orderByDesc(Goods::getSalesVolume)
                .last("LIMIT " + recommendGoodsSize)
                .list();

        if (goodsList.size() < recommendGoodsSize) {
            int goodsCount = goodsList.size();
            List<Long> filterGoodsIds = goodsRecommendQueryBO.getCurrentGoodsIds();
            filterGoodsIds.addAll(goodsList.stream().map(Goods::getId).collect(Collectors.toList()));
            List<Goods> topSalesGoods = this.lambdaQuery()
                    .select(Goods::getId, Goods::getMinPrice, Goods::getMaxPrice, Goods::getWhiteBgImg, Goods::getSubTitle, Goods::getTitle, Goods::getLoveScore, Goods::getCommunityScore)
                    .notIn(CollUtil.isNotEmpty(filterGoodsIds), Goods::getId, filterGoodsIds)
                    .eq(Goods::getDeleted, 0)
                    .eq(Goods::getStatus, GoodsStatus.ON_SALES.getStatus())
                    .orderByDesc(Goods::getSalesVolume)
                    .last("limit " + (RECOMMEND_GOODS_SIZE - goodsCount))
                    .list();
            goodsList.addAll(topSalesGoods);
        }
        List<RecommendGoodsDTO> result = BeanUtil.copyToList(goodsList, RecommendGoodsDTO.class);
        result.forEach(o -> o.setSlug(slugify.slugify(o.getTitle()) + "_" + o.getId()));
        return result;
    }

    @Override
    public List<GoodsHomepageDTO> popularPage(GoodsStatusPageQueryBO goodsStatusPageQueryBO) {
        List<Map<String, Object>> list = this.baseMapper.getPopular(goodsStatusPageQueryBO);

        return list.stream().map(l -> GoodsHomepageDTO.builder().id(Long.parseLong(l.get("id").toString()))
                .title(l.get("title").toString())
                .subTitle(l.get("sub_title").toString())
                .whiteBgImg(l.get("white_bg_img").toString())
                .minPrice(new BigDecimal(l.get("min_price").toString()))
                .maxPrice(new BigDecimal(l.get("max_price").toString()))
                .communityScore(Integer.parseInt(l.get("community_score").toString()))
                .loveScore(Integer.parseInt(l.get("love_score").toString()))
                .availableStock(Long.parseLong(l.get("available_stock").toString()))
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<GoodsSimpleDTO> queryTopXSales(Integer num) {
        List<Goods> goodsList = this.lambdaQuery().select(Goods::getId, Goods::getTitle, Goods::getStatus, Goods::getMinPrice, Goods::getMaxPrice, Goods::getWhiteBgImg, Goods::getCommunityScore, Goods::getSalesVolume).eq(Goods::getStatus, GoodsStatus.ON_SALES.getStatus()).orderByDesc(Goods::getSalesVolume).last("limit " + num).list();
        return BeanUtil.copyToList(goodsList, GoodsSimpleDTO.class);
    }
}
