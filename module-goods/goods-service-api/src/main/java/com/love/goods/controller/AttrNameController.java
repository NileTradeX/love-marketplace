package com.love.goods.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.goods.bo.AttrNameQueryPageBO;
import com.love.goods.bo.AttrNameSaveBO;
import com.love.goods.dto.AttrNameDTO;
import com.love.goods.service.AttrNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("attribute")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AttrNameController {

    private final AttrNameService attrNameService;

    @PostMapping("save")
    public Result<Long> save(@RequestBody AttrNameSaveBO attributeNameSaveBO) {
        return Result.success(attrNameService.save(attributeNameSaveBO));
    }

    @GetMapping("page")
    public Result<Pageable<AttrNameDTO>> page(AttrNameQueryPageBO attrNameQueryPageBO) {
        return Result.success(attrNameService.page(attrNameQueryPageBO));
    }
}
