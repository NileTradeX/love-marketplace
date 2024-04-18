package com.love.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.bo.KeyQueryBO;
import com.love.common.dto.KeyValueDTO;
import com.love.common.entity.KeyValue;
import com.love.common.exception.BizException;
import com.love.common.mapper.KeyValueMapper;
import com.love.common.service.KeyValueService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class KeyValueServiceImpl extends ServiceImpl<KeyValueMapper, KeyValue> implements KeyValueService {

    private boolean isValid(KeyValue keyValue) {
        if (keyValue.getTimeless() == 1) {
            return true;
        } else {
            Date now = new Date();
            return now.after(keyValue.getBeginTime()) && now.before(keyValue.getEndTime());
        }
    }

    @Override
    public KeyValueDTO queryByKey(KeyQueryBO keyQueryBO) {
        KeyValue keyValue = this.lambdaQuery().eq(KeyValue::getKey, keyQueryBO.getKey()).one();
        if (Objects.nonNull(keyValue)) {
            if (isValid(keyValue)) {
                return BeanUtil.copyProperties(keyValue, KeyValueDTO.class);
            }
            throw BizException.build("%s config is invalid", keyQueryBO.getKey());
        }
        throw BizException.build("%s is not configured", keyQueryBO.getKey());
    }
}
