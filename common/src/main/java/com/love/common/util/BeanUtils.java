package com.love.common.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeanUtils {

    private BeanUtils(){

    }

    public static <I, O> List<O> copyList(List<I> list, Class<O> targetClass, PostProcessor<I, O> processor) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream().map(i -> {
            O target = BeanUtil.copyProperties(i, targetClass);
            if (Objects.nonNull(processor)) {
                processor.process(i, target);
            }
            return target;
        }).collect(Collectors.toList());
    }
}
