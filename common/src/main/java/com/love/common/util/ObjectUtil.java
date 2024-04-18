package com.love.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class ObjectUtil {

    public static <T> T ifNull(T src, T def) {
        return Objects.isNull(src) || StringUtils.isBlank(src.toString()) ? def : src;
    }

}
