package com.love.merchant.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.bo.MerPermEditBO;
import com.love.merchant.bo.MerPermQueryPageBO;
import com.love.merchant.bo.MerPermSaveBO;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.service.MerPermService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("mer/perm")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerPermController {

    private final MerPermService merPermService;

    @PostMapping("save")
    public Result<MerPermDTO> save(@RequestBody MerPermSaveBO sysPermSaveBO) {
        return Result.success(merPermService.save(sysPermSaveBO));
    }

    @PostMapping("edit")
    public Result<MerPermDTO> edit(@RequestBody MerPermEditBO sysPermEditBO) {
        return Result.success(merPermService.edit(sysPermEditBO));
    }

    @GetMapping("queryById")
    public Result<MerPermDTO> queryById(IdParam idParam) {
        return Result.success(merPermService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam id) {
        return Result.success(merPermService.deleteById(id));
    }

    @GetMapping("page")
    public Result<Pageable<MerPermDTO>> page(MerPermQueryPageBO sysPermQueryPageBO) {
        return Result.success(merPermService.page(sysPermQueryPageBO));
    }

    @GetMapping("tree")
    public Result<List<MerPermDTO>> tree(IdParam idParam) {
        return Result.success(merPermService.tree(idParam.getId(), true));
    }
}
