package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.slugify.Slugify;
import com.love.common.enums.YesOrNo;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.ObjectUtil;
import com.love.common.util.PageUtil;
import com.love.goods.bo.*;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.CategoryTreeDTO;
import com.love.goods.dto.PublishCategoryIdDTO;
import com.love.goods.entity.Category;
import com.love.goods.enums.CategoryType;
import com.love.goods.enums.GoodsType;
import com.love.goods.mapper.CategoryMapper;
import com.love.goods.service.CategoryService;
import com.love.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);

    @Autowired
    private GoodsService goodsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(CategorySaveBO categorySaveBO) {
        Category category = BeanUtil.copyProperties(categorySaveBO, Category.class);
        category.setName(categorySaveBO.getName().trim());
        category.setLevel(ObjectUtil.ifNull(categorySaveBO.getLevel(), 1));
        category.setSortNum(ObjectUtil.ifNull(categorySaveBO.getSortNum(), 1));
        category.setStatus(YesOrNo.YES.getVal());

        if (Objects.isNull(category.getPid())) {
            throw BizException.build("Father category can't be null");
        }

        if (category.getLevel() > 1 && category.getPid() == 0) {
            throw BizException.build("Level can't match the father category");
        }

        boolean exists = this.lambdaQuery().apply("UPPER(name) = '" + category.getName().toUpperCase() + "'").eq(Category::getType, category.getType()).exists();
        if (exists) {
            throw BizException.build("category name %s is already used", category.getName());
        }
        if (CategoryType.DISPLAY.getType() == categorySaveBO.getType()) {
            category.setAlias(categorySaveBO.getAlias().trim());
            exists = this.lambdaQuery().apply("UPPER(alias) = '" + category.getAlias().toUpperCase() + "'").eq(Category::getType, category.getType()).exists();
            if (exists) {
                throw BizException.build("category alias %s is already used", category.getAlias());
            }
        }
        boolean result = this.save(category);
        if (result) {
            if (CategoryType.DISPLAY.getType() == categorySaveBO.getType()) {
                if (categorySaveBO.getLevel() > 1 && Objects.nonNull(categorySaveBO.getIds())) {
                    updateParentDisplayCategoryIds(categorySaveBO.getPid());
                }
            }
        }
        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO edit(CategoryEditBO categoryEditBO) {
        Category category = BeanUtil.copyProperties(categoryEditBO, Category.class);
        category.setName(categoryEditBO.getName().trim());
        boolean exists = this.lambdaQuery().apply("UPPER(name) = '" + category.getName().toUpperCase() + "'").eq(Category::getType, category.getType()).ne(Category::getId, category.getId()).exists();
        if (exists) {
            throw BizException.build("category name %s is already used", category.getName());
        }

        if (CategoryType.DISPLAY.getType() == categoryEditBO.getType()) {
            category.setAlias(categoryEditBO.getAlias().trim());
            exists = this.lambdaQuery().apply("UPPER(alias) = '" + category.getAlias().toUpperCase() + "'").eq(Category::getType, category.getType()).ne(Category::getId, category.getId()).exists();
            if (exists) {
                throw BizException.build("category alias %s is already used", category.getAlias());
            }
        }
        boolean result = this.updateById(category);
        if (result) {
            if (categoryEditBO.getLevel() > 1 && Objects.nonNull(categoryEditBO.getIds())) {
                updateParentDisplayCategoryIds(categoryEditBO.getPid());
            }
        }
        return BeanUtil.copyProperties(this.getById(category.getId()), CategoryDTO.class);
    }

    @Override
    public CategoryDTO queryById(IdParam idParam) {
        Category category = this.getById(idParam.getId());
        if (Objects.isNull(category)) {
            throw BizException.build("Category not found");
        }
        return BeanUtil.copyProperties(category, CategoryDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(IdParam idParam) {
        List<Long> cateIds = new ArrayList<>();
        Category category = this.getById(idParam.getId());
        List<Category> childCategories = this.lambdaQuery().eq(Category::getPid, idParam.getId()).list();
        long count = goodsService.countByCategoryId(category.getId(), category.getLevel());
        if (count > 0) {
            throw BizException.build("Unable to delete category. Products are associated with this category");
        }
        boolean result = this.removeById(idParam.getId());
        if (result) {
            deleteByPid(idParam.getId());
            if (CategoryType.PUBLISHING.getType() == category.getType()) {
                cateIds.add(category.getId());
                cateIds.addAll(childCategories.stream().map(Category::getId).collect(Collectors.toList()));
                if (CollUtil.isNotEmpty(cateIds)) {
                    updateCategoryIdsById(cateIds);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByPid(Long pid) {
        return this.remove(new LambdaQueryWrapper<Category>().eq(Category::getPid, pid));
    }

    @Override
    public Pageable<CategoryDTO> page(CategoryQueryPageBO categoryQueryPageBO) {
        Page<Category> page = this.lambdaQuery()
                .eq(Objects.nonNull(categoryQueryPageBO.getLevel()), Category::getLevel, categoryQueryPageBO.getLevel())
                .eq(Category::getStatus, YesOrNo.YES.getVal())
                .eq(Category::getType, categoryQueryPageBO.getType())
                .page(new Page<>(categoryQueryPageBO.getPageNum(), categoryQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, CategoryDTO.class);
    }

    @Override
    public List<CategoryTreeDTO> tree(CategoryQueryTreeBO categoryQueryTreeBO) {
        List<CategoryTreeDTO> result = new ArrayList<>();

        if (categoryQueryTreeBO.isAttachGoodsType()) {
            for (GoodsType goodsType : GoodsType.values()) {
                if (goodsType != GoodsType.DIGITAL) {
                    CategoryTreeDTO categoryTreeDTO = new CategoryTreeDTO();
                    categoryTreeDTO.setName(goodsType.getName());
                    categoryTreeDTO.setId(( long ) goodsType.getType());
                    result.add(categoryTreeDTO);
                }
            }
        }

        List<Category> firstLevelList = this.lambdaQuery().eq(Category::getType, categoryQueryTreeBO.getType()).eq(Category::getLevel, categoryQueryTreeBO.getLevel()).eq(Category::getStatus, YesOrNo.YES.getVal()).orderByAsc(Category::getSortNum).list();
        if (CollUtil.isNotEmpty(firstLevelList)) {
            List<CategoryTreeDTO> list = new ArrayList<>();
            for (Category category : firstLevelList) {
                CategoryTreeDTO target = BeanUtil.copyProperties(category, CategoryTreeDTO.class);
                target.setSlug(slugify.slugify(target.getAlias()));
                target.setChildren(querySubCategory(target.getId()));
                list.add(target);
            }

            if (categoryQueryTreeBO.isAttachGoodsType() && list.size() > 0) {
                result.forEach(i -> i.setChildren(list));
                return result;
            } else {
                return list;
            }
        }
        return result;
    }

    private List<CategoryTreeDTO> querySubCategory(Long pid) {
        List<Category> categoryList = this.lambdaQuery().eq(Category::getPid, pid).eq(Category::getStatus, YesOrNo.YES.getVal()).orderByAsc(Category::getSortNum).list();
        if (CollUtil.isNotEmpty(categoryList)) {
            return categoryList.stream().map(category -> {
                CategoryTreeDTO target = BeanUtil.copyProperties(category, CategoryTreeDTO.class);
                target.setSlug(slugify.slugify(target.getAlias()));
                return target;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<CategoryDTO> queryByPid(CategoryQueryByPidBO categoryQueryByPidBO) {
        if (Objects.isNull(categoryQueryByPidBO.getPid())) {
            categoryQueryByPidBO.setPid(0);
        }
        List<Category> categoryList = this.lambdaQuery().eq(Category::getPid, categoryQueryByPidBO.getPid()).list();
        return BeanUtil.copyToList(categoryList, CategoryDTO.class);
    }

    @Override
    public PublishCategoryIdDTO queryPublishCategoriesById(PublishCategoryQueryBO publishCategoryQueryBO) {
        Set<Long> firstCatIds = new HashSet<>();
        Set<Long> secondCatIds = new HashSet<>();
        Set<Long> tempIds = new HashSet<>();
        if (CollUtil.isNotEmpty(publishCategoryQueryBO.getFirstCateIds())) {
            List<Category> categories = this.lambdaQuery().in(Category::getId, publishCategoryQueryBO.getFirstCateIds()).list();
            List<String> ids = categories.stream().map(Category::getIds).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(ids)) {
                ids.forEach(o -> tempIds.addAll(Arrays.stream(o.split(",")).map(Long::valueOf).collect(Collectors.toList())));
                List<Category> firstCategories = this.lambdaQuery().in(Category::getId, tempIds).list();
                firstCatIds.addAll(firstCategories.stream().filter(o -> o.getLevel() == 1).map(Category::getId).collect(Collectors.toList()));
                secondCatIds.addAll(firstCategories.stream().filter(o -> o.getLevel() > 1).map(Category::getId).collect(Collectors.toList()));
            }
        }

        if (CollUtil.isNotEmpty(publishCategoryQueryBO.getSecondCateIds())) {
            List<Category> categories = this.lambdaQuery().in(Category::getId, publishCategoryQueryBO.getSecondCateIds()).list();
            List<String> ids = categories.stream().map(Category::getIds).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(ids)) {
                tempIds.clear();
                secondCatIds.clear();
                ids.forEach(o -> tempIds.addAll(Arrays.stream(o.split(",")).map(Long::valueOf).collect(Collectors.toList())));
                List<Category> secondCategories = this.lambdaQuery().in(Category::getId, tempIds).list();
                secondCatIds.addAll(secondCategories.stream().filter(o -> o.getLevel() > 1).map(Category::getId).collect(Collectors.toList()));
            }
        }
        return PublishCategoryIdDTO.builder().firstCateIds(firstCatIds).secondCateIds(secondCatIds).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateParentDisplayCategoryIds(Long pId) {
        List<Category> categories = this.lambdaQuery().select(Category::getIds).eq(Category::getPid, pId).eq(Category::getType, CategoryType.DISPLAY.getType()).list();
        Category parentCategory = this.getById(pId);
        List<String> cateIds = new ArrayList<>();
        if (CollUtil.isNotEmpty(categories)) {
            categories.forEach(category -> {
                if (Objects.nonNull(category)) {
                    cateIds.add(parentCategory.getIds());
                    cateIds.add(category.getIds());
                }
            });
        }
        if (CollUtil.isNotEmpty(cateIds)) {
            String ids = CollUtil.join(cateIds, ",");
            String displayIds = String.join(",", CollUtil.distinct(Arrays.asList(ids.split(","))));
            this.lambdaUpdate().set(Category::getIds, displayIds).eq(Category::getId, pId).eq(Category::getType, CategoryType.DISPLAY.getType()).update();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCategoryIdsById(List<Long> idList) {
        idList.forEach(o -> {
            List<Category> categories = this.lambdaQuery().select(Category::getIds, Category::getId)
                    .eq(Category::getType, CategoryType.DISPLAY.getType())
                    .like(Category::getIds, String.valueOf(o)).list();
            categories.forEach(c -> {
                String ids = c.getIds().replaceAll("" + o + "(,)?", "").replaceAll(",$", "");
                this.lambdaUpdate().set(Category::getIds, ids).eq(Category::getId, c.getId()).update();
            });
        });
    }

}
