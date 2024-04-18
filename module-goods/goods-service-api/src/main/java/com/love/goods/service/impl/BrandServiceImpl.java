package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.util.PageUtil;
import com.love.goods.bo.BrandQueryListBO;
import com.love.goods.bo.BrandQueryPageBO;
import com.love.goods.bo.BrandSaveBO;
import com.love.goods.dto.BrandDTO;
import com.love.goods.entity.Brand;
import com.love.goods.enums.BrandStatus;
import com.love.goods.mapper.BrandMapper;
import com.love.goods.service.BrandService;
import com.love.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private GoodsService goodsService;

    @Override
    @Transactional
    public Long save(BrandSaveBO brandSaveBO) {
        Brand brand = BeanUtil.copyProperties(brandSaveBO, Brand.class);
        brand.setName(brand.getName().trim());
        this.saveOrUpdate(brand);
        return brand.getId();
    }

    @Override
    @Transactional
    public Boolean saveBatch(List<BrandSaveBO> brandSaveBOList) {
        if (1 != brandSaveBOList.stream().map(BrandSaveBO::getMerchantId).distinct().count()) {
            throw BizException.build("Not the same merchant brand!");
        }

        Long merchantId = brandSaveBOList.get(0).getMerchantId();
        List<Brand> brands = this.lambdaQuery().eq(Brand::getMerchantId, merchantId).list();
        List<Long> brandIdList = brands.stream().map(Brand::getId).collect(Collectors.toList());

        List<BrandSaveBO> updateList = brandSaveBOList.stream().filter(x -> Objects.nonNull(x.getId())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(updateList)) {
            this.updateBatchById(BeanUtil.copyToList(updateList, Brand.class));
        }

        List<Long> updateIdList = updateList.stream().map(BrandSaveBO::getId).collect(Collectors.toList());
        brandIdList.removeAll(updateIdList);
        if (CollUtil.isNotEmpty(brandIdList)) {
            this.removeByIds(brandIdList);
        }

        List<BrandSaveBO> saveList = brandSaveBOList.stream().filter(x -> Objects.isNull(x.getId())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(saveList)) {
            this.saveBatch(BeanUtil.copyToList(saveList, Brand.class));
        }
        return true;
    }

    @Override
    public BrandDTO queryById(IdParam idParam) {
        Brand brand = this.getById(idParam.getId());
        if (Objects.isNull(brand)) {
            throw BizException.build("Brand not found!");
        }
        return BeanUtil.copyProperties(brand, BrandDTO.class);
    }

    @Override
    public boolean deleteById(IdParam id) {
        if (goodsService.countByBrandId(id.getId()) > 0) {
            throw BizException.build("Unable to delete brand. Products are associated with this brand");
        }
        return this.removeById(id);
    }

    @Override
    public List<BrandDTO> queryByMerchantId(BrandQueryListBO brandQueryListBO) {
        List<Brand> list = this.lambdaQuery().eq(Brand::getMerchantId, brandQueryListBO.getMerchantId()).list();
        return BeanUtil.copyToList(list, BrandDTO.class);
    }

    @Override
    public Pageable<BrandDTO> page(BrandQueryPageBO brandQueryPageBO) {
        Page<Brand> page = this.lambdaQuery().eq(Objects.nonNull(brandQueryPageBO.getStatus()), Brand::getStatus, brandQueryPageBO.getStatus()).page(new Page<>(brandQueryPageBO.getPageNum(), brandQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, BrandDTO.class);
    }

    @Override
    public boolean updateStatus(Long merchantId, BrandStatus brandStatus) {
        return this.lambdaUpdate().eq(Brand::getMerchantId, merchantId).set(Brand::getStatus, brandStatus.getStatus()).update();
    }

    @Override
    public List<BrandDTO> queryByIds(IdsParam idsParam) {
        if (CollUtil.isEmpty(idsParam.getIdList())) {
            return Lists.newArrayList();
        }

        List<Brand> brandList = this.lambdaQuery().in(Brand::getId, idsParam.getIdList()).list();
        return BeanUtil.copyToList(brandList, BrandDTO.class);
    }

}
