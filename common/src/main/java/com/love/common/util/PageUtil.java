package com.love.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.love.common.page.Pageable;

import java.util.Collections;

public class PageUtil {

    public static <T> Pageable<T> emptyPage(long current, long size) {
        Pageable<T> emptyPage = new Pageable<>(current, size);
        emptyPage.setRecords(Collections.emptyList());
        return emptyPage;
    }


    public static <T> Pageable<T> toPage(IPage<?> page, Class<T> tClass) {
        return toPage(page, tClass, null);
    }

    public static <I, O> Pageable<O> toPage(IPage<I> page, Class<O> tClass, PostProcessor<I, O> processor) {
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return emptyPage(page.getCurrent(), page.getSize());
        }

        Pageable<O> pageable = new Pageable<>(page.getCurrent(), page.getSize());
        pageable.setTotal(page.getTotal());
        pageable.setPages(page.getPages());
        pageable.setRecords(BeanUtils.copyList(page.getRecords(), tClass, processor));
        return pageable;
    }
}
