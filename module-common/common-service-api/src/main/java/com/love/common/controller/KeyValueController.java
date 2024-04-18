package com.love.common.controller;

import com.love.common.bo.KeyQueryBO;
import com.love.common.dto.KeyValueDTO;
import com.love.common.result.Result;
import com.love.common.service.KeyValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kv")
public class KeyValueController {

    private final KeyValueService keyValueService;

    @GetMapping("key")
    public Result<KeyValueDTO> queryByKey(KeyQueryBO keyQueryBO) {
        return Result.success(keyValueService.queryByKey(keyQueryBO));
    }
}
