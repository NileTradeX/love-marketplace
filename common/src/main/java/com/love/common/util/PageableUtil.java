package com.love.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.love.common.page.Pageable;

import java.util.Collections;

public class PageableUtil {

    public static <T> Pageable<T> emptyPage(long current, long size) {
        Pageable<T> emptyPage = new Pageable<>(current, size);
        emptyPage.setRecords(Collections.emptyList());
        return emptyPage;
    }

    public static <I, T> Pageable<T> toPage(Pageable<I> page, Class<T> tClass) {
        return toPage(page, tClass, null);
    }


    public static <I, O> Pageable<O> toPage(Pageable<I> page, Class<O> tClass, PostProcessor<I, O> processor) {
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return emptyPage(page.getPageNum(), page.getPageSize());
        }

        Pageable<O> pageable = Pageable.<O>builder().pageNum(page.getPageNum()).pageSize(page.getPageSize()).build();
        pageable.setTotal(page.getTotal());
        pageable.setPages(page.getPages());
        pageable.setRecords(BeanUtils.copyList(page.getRecords(), tClass, processor));
        return pageable;
    }
}
