package com.love.common.util;

import cn.hutool.core.util.StrUtil;

public class SortUtil {
    private final String[] arr;

    public SortUtil(String sort) {
        if (sort == null || !sort.contains("_")) {
            throw new IllegalArgumentException("sort should be like xx_asc or xx_desc");
        }

        this.arr = sort.split("_");
        if (this.arr.length != 2) {
            throw new IllegalArgumentException("sort should be like xx_asc or xx_desc");
        }
    }

    public String getSortField() {
        return StrUtil.toUnderlineCase(this.arr[0]);
    }

    public boolean isAsc() {
        return this.arr[1].equalsIgnoreCase("asc");
    }
}
