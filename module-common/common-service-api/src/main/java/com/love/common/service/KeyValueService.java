package com.love.common.service;

import com.love.common.bo.KeyQueryBO;
import com.love.common.dto.KeyValueDTO;

public interface KeyValueService {

    KeyValueDTO queryByKey(KeyQueryBO keyQueryBO);
}
