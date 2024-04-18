package com.love.influencer.controller;

import com.love.common.result.Result;
import com.love.influencer.bo.InfUserHitsSaveBO;
import com.love.influencer.dto.InfUserHitsDTO;
import com.love.influencer.service.InfUserHitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("influencer/hits")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfUserHitsController {

    private final InfUserHitsService infUserHitsService;

    @PostMapping("save")
    public Result<InfUserHitsDTO> save(@RequestBody InfUserHitsSaveBO infUserHitsSaveBO) {
        return Result.success(infUserHitsService.save(infUserHitsSaveBO));
    }

    @PostMapping("edit")
    public Result<InfUserHitsDTO> edit(@RequestBody InfUserHitsSaveBO infUserHitsSaveBO) {
        return Result.success(infUserHitsService.save(infUserHitsSaveBO));
    }

}
