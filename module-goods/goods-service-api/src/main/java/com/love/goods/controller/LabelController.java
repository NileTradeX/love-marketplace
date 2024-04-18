package com.love.goods.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.goods.bo.LabelQueryPageBO;
import com.love.goods.bo.LabelSaveBO;
import com.love.goods.dto.LabelDTO;
import com.love.goods.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("label")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LabelController {

    private final LabelService labelService;

    @PostMapping("save")
    public Result<Long> save(@RequestBody LabelSaveBO labelSaveBO) {
        return Result.success(labelService.save(labelSaveBO));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(labelService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<LabelDTO>> page(LabelQueryPageBO labelQueryPageBO) {
        return Result.success(labelService.page(labelQueryPageBO));
    }

}
