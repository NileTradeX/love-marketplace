package com.love.backend.controller;

import com.love.backend.manager.LabelManager;
import com.love.backend.model.param.LabelQueryPageParam;
import com.love.backend.model.vo.LabelVO;
import com.love.common.page.Pageable;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods/label")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "GoodsLabelApi", description = "All Goods label operation")
public class LabelController {

    private final LabelManager labelManager;

    @GetMapping("page")
    @Operation(operationId = "queryLabelPage")
    public Result<Pageable<LabelVO>> page(LabelQueryPageParam labelQueryPageParam) {
        return Result.success(labelManager.page(labelQueryPageParam));
    }
}
